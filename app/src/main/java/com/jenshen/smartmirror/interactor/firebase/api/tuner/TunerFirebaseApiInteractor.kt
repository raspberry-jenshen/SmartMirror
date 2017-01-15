package com.jenshen.smartmirror.interactor.firebase.api.tuner

import android.content.Context
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.FirebaseChildEvent
import com.jenshen.smartmirror.data.firebase.model.configuration.*
import com.jenshen.smartmirror.data.firebase.model.mirror.MirrorConfigurationInfo
import com.jenshen.smartmirror.data.firebase.model.tuner.TunerSubscription
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetSize
import com.jenshen.smartmirror.data.model.mirror.EditMirrorModel
import com.jenshen.smartmirror.data.model.mirror.MirrorModel
import com.jenshen.smartmirror.data.model.widget.WidgetConfigurationModel
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.manager.firebase.api.ApiManager
import com.jenshen.smartmirror.manager.firebase.api.mirror.MirrorApiManager
import com.jenshen.smartmirror.manager.firebase.api.tuner.TunerApiManager
import com.jenshen.smartmirror.manager.job.IJobManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshensoft.widgetview.entity.WidgetPosition
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.*

class TunerFirebaseApiInteractor(private var context: Context,
                                 private var apiManager: ApiManager,
                                 private var preferencesManager: PreferencesManager,
                                 private var tunerApiManager: TunerApiManager,
                                 private var mirrorApiManager: MirrorApiManager,
                                 private var jobManager: IJobManager) : TunerApiInteractor {

    /* mirror */

    override fun subscribeOnMirror(mirrorId: String): Completable {
        return apiManager.getMirror(mirrorId)
                .switchIfEmpty { throw RuntimeException(context.getString(R.string.error_cant_find_mirror)) }
                .flatMapCompletable { mirror ->
                    getTunerSession()
                            .flatMapCompletable {
                                tunerApiManager.addSubscriberToMirror(it.key, mirrorId)
                                        .andThen(tunerApiManager.addSubscriptionInTuner(it.key, mirrorId, mirror))
                            }
                            .andThen(tunerApiManager.setFlagForWaitingSubscribersOnMirror(false, mirrorId))
                }
    }

    override fun unsubscribeFromMirror(mirrorId: String): Completable {
        return apiManager.getMirror(mirrorId)
                .switchIfEmpty { throw RuntimeException(context.getString(R.string.error_cant_find_mirror)) }
                .flatMapCompletable { mirror ->
                    getTunerSession()
                            .flatMapCompletable {
                                tunerApiManager.removeSubscriberFromMirror(it.key, mirrorId)
                                        .andThen(tunerApiManager.removeSubscriptionFromTuner(it.key, mirrorId))
                                        .andThen(Single.fromCallable { mirror }
                                                .flatMapCompletable {
                                                    if (it.subscribers != null && it.subscribers!!.size == 1) {
                                                        tunerApiManager.setFlagForWaitingSubscribersOnMirror(true, mirrorId)
                                                    } else {
                                                        Completable.complete()
                                                    }
                                                })
                                        .andThen(jobManager.onDeleteJob(mirrorId))
                            }
                }
    }

    override fun switchFlagForWaitingTuner(mirrorId: String): Completable {
        return apiManager.getMirror(mirrorId)
                .switchIfEmpty { throw RuntimeException(context.getString(R.string.error_cant_find_mirror)) }
                .flatMapCompletable { tunerApiManager.setFlagForWaitingSubscribersOnMirror(!it.isWaitingForTuner, mirrorId) }
    }

    override fun setSelectedConfigurationKeyForMirror(configurationId: String?, mirrorId: String): Completable {
        if (configurationId == null) {
            return tunerApiManager.deleteSelectedConfigurationKeyForMirror(mirrorId)
        }
        return tunerApiManager.setSelectedConfigurationKeyForMirror(configurationId, mirrorId)
    }

    override fun deleteConfigurationForMirror(configurationId: String, mirrorId: String, isSelected: Boolean): Completable {
        return tunerApiManager.deleteMirrorConfigurationInfoForMirror(configurationId, mirrorId)
                .andThen(tunerApiManager.deleteMirrorConfiguration(configurationId))
                .andThen(jobManager.onDeleteJob(mirrorId, configurationId))
    }

    /* tuner */

    override fun fetchTunerSubscriptions(): Flowable<MirrorModel> {
        return getTunerSession()
                .flatMapPublisher { tunerApiManager.observeTunerSubscriptions(it.key) }
                .flatMapSingle {
                    val EMPTY = "EMPTY"
                    Single.zip(
                            tunerApiManager.getMirrorConfigurationsInfo(it.dataSnapshot.key)
                                    .toSingle(hashMapOf()),
                            tunerApiManager.getSelectedConfigurationKeyForMirror(it.dataSnapshot.key)
                                    .toSingle(EMPTY),
                            BiFunction { configurations: HashMap<String, MirrorConfigurationInfo>,
                                         selectedConfigurationKey: String ->
                                MirrorModel(it.dataSnapshot.key,
                                        it.dataSnapshot.getValue(TunerSubscription::class.java),
                                        it.eventType == FirebaseChildEvent.CHILD_REMOVED,
                                        if (selectedConfigurationKey == EMPTY) null else selectedConfigurationKey,
                                        configurations)
                            })
                }
    }

    /* widget */

    override fun fetchWidgets(): Flowable<DataSnapshotWithKey<WidgetInfo>> {
        return tunerApiManager.observeWidgets()
                .map { DataSnapshotWithKey(it.dataSnapshot.key, it.dataSnapshot.getValue(WidgetInfo::class.java)) }
    }

    override fun addWidget(name: String, width: Int, height: Int): Single<String> {
        return Single.fromCallable { WidgetInfo(name, WidgetSize(width, height)) }
                .flatMap { tunerApiManager.addWidget(it) }
    }

    /* mirror configurations */

    override fun saveMirrorConfiguration(editMirrorModel: EditMirrorModel): Completable {
        return Single.fromCallable {
            MirrorConfiguration(
                    editMirrorModel.mirrorKey,
                    ContainerSize(editMirrorModel.columnsCount, editMirrorModel.rowsCount),
                    editMirrorModel.title,
                    editMirrorModel.orientationMode.index,
                    editMirrorModel.isEnablePrecipitation,
                    editMirrorModel.userInfoKey)
        }
                .flatMapCompletable { mirrorConfiguration ->
                    if (editMirrorModel.configurationKey == null) {
                        return@flatMapCompletable tunerApiManager.createMirrorConfiguration(mirrorConfiguration)
                                .doOnSuccess { editMirrorModel.configurationKey = it }
                                .toCompletable()
                    } else {
                        return@flatMapCompletable tunerApiManager.editMirrorConfiguration(editMirrorModel.configurationKey!!, mirrorConfiguration)
                    }
                }
                .andThen(updateWidgets(editMirrorModel))
                .andThen(updateConfigurationInfoForMirror(editMirrorModel))
                .andThen(getTunerSession()
                        .flatMapCompletable { tunerApiManager.updateSubscriptionInTuner(it.key, editMirrorModel.mirrorKey) })
    }

    override fun getMirrorConfiguration(configurationKey: String): Single<EditMirrorModel> {
        return mirrorApiManager.getMirrorConfiguration(configurationKey)
                .flatMap { dataSnapshotWithKey ->
                    Observable.fromIterable(dataSnapshotWithKey.data.widgets?.toList() ?: mutableListOf())
                            .flatMapSingle { pair ->
                                val widgetConfiguration = pair.second
                                tunerApiManager.getWidget(pair.second.widgetKey)
                                        .map { snapshotsWithKey ->
                                            WidgetConfigurationModel(
                                                    WidgetKey(snapshotsWithKey.key),
                                                    snapshotsWithKey.data,
                                                    widgetConfiguration.tunerKey,
                                                    widgetConfiguration.phrase,
                                                    pair.first,
                                                    WidgetPosition(widgetConfiguration.topLeftCorner.column, widgetConfiguration.topLeftCorner.row,
                                                            widgetConfiguration.topRightCorner.column, widgetConfiguration.topRightCorner.row,
                                                            widgetConfiguration.bottomLeftCorner.column, widgetConfiguration.bottomLeftCorner.row,
                                                            widgetConfiguration.bottomRightCorner.column, widgetConfiguration.bottomRightCorner.row))
                                        }
                            }
                            .toList()
                            .map { widgetModels ->
                                EditMirrorModel(
                                        dataSnapshotWithKey.data.mirrorKey,
                                        dataSnapshotWithKey.data.containerSize.column,
                                        dataSnapshotWithKey.data.containerSize.row,
                                        dataSnapshotWithKey.data.title,
                                        dataSnapshotWithKey.data.isEnablePrecipitation,
                                        OrientationMode.toOrientationMode(dataSnapshotWithKey.data.orientationMode),
                                        dataSnapshotWithKey.key,
                                        dataSnapshotWithKey.data.userInfoKey,
                                        widgetModels)
                            }
                }
    }

    override fun isEnablePrecipitationOnMirror(configurationKey: String): Single<Boolean> {
        return tunerApiManager.isEnablePrecipitationInConfiguration(configurationKey)
    }

    override fun isUserInfoOnMirror(configurationKey: String): Single<Boolean> {
        return tunerApiManager.getUserInfoKeyInConfiguration(configurationKey)
                .isEmpty
                .map { !it }
    }

    override fun getOrientationModeInConfiguration(configurationKey: String): Single<OrientationMode> {
        return tunerApiManager.getOrientationModeInConfiguration(configurationKey)
                .defaultIfEmpty(OrientationMode.PORTRAIT)
                .toSingle()
    }

    override fun setOrientationModeInConfiguration(configurationKey: String, orientationMode: OrientationMode): Completable {
        return tunerApiManager.setOrientationModeInConfiguration(configurationKey, orientationMode)
    }

    override fun setEnablePrecipitationOnMirror(configurationKey: String, isEnable: Boolean): Completable {
        return tunerApiManager.setEnablePrecipitationInConfiguration(configurationKey, isEnable)
    }

    override fun setEnableUserInfoOnMirror(configurationKey: String, isEnable: Boolean): Single<String> {
        return Single.fromCallable { preferencesManager.getSession()!! }
                .cast(TunerSession::class.java)
                .flatMap {
                    tunerApiManager.setUserInfoKeyInConfiguration(configurationKey, if (isEnable) it.key else null)
                            .toSingle { it.key }
                }
    }


    /* private methods */

    private fun updateWidgets(editMirrorModel: EditMirrorModel): Completable {
        return Completable.defer {
            Observable.fromIterable(editMirrorModel.widgets)
                    .flatMapCompletable { widgetModel ->
                        val widgetConfiguration = WidgetConfiguration(
                                widgetModel.widgetKey.key,
                                Corner(widgetModel.widgetPosition!!.topLeftColumnLine, widgetModel.widgetPosition!!.topLeftRowLine),
                                Corner(widgetModel.widgetPosition!!.topRightColumnLine, widgetModel.widgetPosition!!.topRightRowLine),
                                Corner(widgetModel.widgetPosition!!.bottomLeftColumnLine, widgetModel.widgetPosition!!.bottomLeftRowLine),
                                Corner(widgetModel.widgetPosition!!.bottomRightColumnLine, widgetModel.widgetPosition!!.bottomRightRowLine),
                                widgetModel.tunerKey,
                                widgetModel.phrase)

                        if (widgetModel.key == null) {
                            return@flatMapCompletable tunerApiManager.createWidgetInConfiguration(editMirrorModel.configurationKey!!, widgetConfiguration)
                                    .doOnSuccess { widgetModel.key = it }
                                    .flatMapCompletable {
                                        jobManager.onCreateJob(
                                                editMirrorModel.mirrorKey,
                                                editMirrorModel.configurationKey!!,
                                                widgetModel.key!!,
                                                widgetModel.widgetKey)
                                    }
                        } else {
                            if (widgetModel.isDeleted) {
                                return@flatMapCompletable tunerApiManager.deleteWidgetInConfiguration(editMirrorModel.configurationKey!!, widgetModel.key!!)
                                        .andThen(
                                                jobManager.onDeleteJob(
                                                        editMirrorModel.mirrorKey,
                                                        editMirrorModel.configurationKey!!,
                                                        widgetModel.key!!,
                                                        widgetModel.widgetKey))
                            } else {
                                return@flatMapCompletable tunerApiManager.editWidgetInConfiguration(editMirrorModel.configurationKey!!, widgetModel.key!!, widgetConfiguration)
                            }
                        }
                    }
        }
    }

    private fun updateConfigurationInfoForMirror(editMirrorModel: EditMirrorModel): Completable {
        return Completable.defer {
            tunerApiManager.createOrEditMirrorConfigurationInfoForMirror(
                    editMirrorModel.configurationKey!!,
                    editMirrorModel.mirrorKey,
                    MirrorConfigurationInfo(editMirrorModel.title, Calendar.getInstance().time.time))
        }
    }

    private fun getTunerSession(): Single<TunerSession> {
        return Single.fromCallable { preferencesManager.getSession()!! }
                .cast(TunerSession::class.java)
    }
}
