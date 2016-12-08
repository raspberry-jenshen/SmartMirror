package com.jenshen.smartmirror.interactor.firebase.api.tuner

import android.content.Context
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.FirebaseChildEvent
import com.jenshen.smartmirror.data.firebase.model.configuration.Corner
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.data.firebase.model.configuration.WidgetConfiguration
import com.jenshen.smartmirror.data.firebase.model.mirror.MirrorConfigurationInfo
import com.jenshen.smartmirror.data.firebase.model.tuner.TunerSubscription
import com.jenshen.smartmirror.data.firebase.model.widget.Size
import com.jenshen.smartmirror.data.firebase.model.widget.Widget
import com.jenshen.smartmirror.data.model.EditMirrorModel
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.data.model.WidgetConfigurationModel
import com.jenshen.smartmirror.manager.firebase.api.ApiManager
import com.jenshen.smartmirror.manager.firebase.api.mirror.MirrorApiManager
import com.jenshen.smartmirror.manager.firebase.api.tuner.TunerApiManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshensoft.widgetview.entity.WidgetPosition
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.*
import javax.inject.Inject

class TunerFirebaseApiInteractor @Inject constructor(private var context: Context,
                                                     private var apiManager: ApiManager,
                                                     private var preferencesManager: PreferencesManager,
                                                     private var tunerApiManager: TunerApiManager,
                                                     private var mirrorApiManager: MirrorApiManager) : TunerApiInteractor {

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
        return tunerApiManager.setSelectedConfigurationKeyForMirror(configurationId!!, mirrorId)
    }

    override fun deleteConfigurationForMirror(configurationId: String, mirrorId: String, isSelected: Boolean): Completable {
        return tunerApiManager.deleteMirrorConfigurationInfoForMirror(configurationId, mirrorId)
                .andThen(tunerApiManager.deleteMirrorConfiguration(configurationId))
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

    override fun fetchWidgets(): Flowable<DataSnapshotWithKey<Widget>> {
        return tunerApiManager.observeWidgets()
                .map { DataSnapshotWithKey(it.dataSnapshot.key, it.dataSnapshot.getValue(Widget::class.java)) }
    }

    override fun addWidget(name: String, width: Int, height: Int): Single<String> {
        return Single.fromCallable { Widget(name, Size(width, height)) }
                .flatMap { tunerApiManager.addWidget(it) }
    }

    /* mirror configurations */

    override fun saveMirrorConfiguration(editMirrorModel: EditMirrorModel): Completable {
        return Single.fromCallable { MirrorConfiguration(editMirrorModel.mirrorKey, editMirrorModel.title) }
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
                .andThen(updateConfigurationForMirror(editMirrorModel))
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
                                                    snapshotsWithKey.key,
                                                    snapshotsWithKey.data,
                                                    snapshotsWithKey.key,
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
                                        dataSnapshotWithKey.data.title,
                                        widgetModels,
                                        dataSnapshotWithKey.key)
                            }
                }
    }

    /* private methods */

    private fun updateWidgets(editMirrorModel: EditMirrorModel): Completable {
        return Completable.defer {
            Observable.fromIterable(editMirrorModel.list)
                    .flatMapCompletable { widgetModel ->
                        val widgetConfiguration = WidgetConfiguration(widgetModel.widgetKey,
                                Corner(widgetModel.widgetPosition!!.topLeftColumnLine, widgetModel.widgetPosition!!.topLeftRowLine),
                                Corner(widgetModel.widgetPosition!!.topRightColumnLine, widgetModel.widgetPosition!!.topRightRowLine),
                                Corner(widgetModel.widgetPosition!!.bottomLeftColumnLine, widgetModel.widgetPosition!!.bottomLeftRowLine),
                                Corner(widgetModel.widgetPosition!!.bottomRightColumnLine, widgetModel.widgetPosition!!.bottomRightRowLine))

                        if (widgetModel.key == null) {
                            tunerApiManager.createWidgetInConfiguration(editMirrorModel.configurationKey!!, widgetConfiguration)
                                    .doOnSuccess { widgetModel.key = it }
                                    .toCompletable()
                        } else {
                            tunerApiManager.editWidgetInConfiguration(editMirrorModel.configurationKey!!, widgetModel.key!!, widgetConfiguration)
                        }
                    }
        }
    }

    private fun updateConfigurationForMirror(editMirrorModel: EditMirrorModel): Completable {
        return Completable.defer {
            tunerApiManager.createOrEditMirrorConfigurationInfoForMirror(
                    editMirrorModel.mirrorKey,
                    editMirrorModel.configurationKey!!,
                    MirrorConfigurationInfo(editMirrorModel.title, Calendar.getInstance().time.time))
        }
    }

    private fun getTunerSession(): Single<TunerSession> {
        return Single.fromCallable { preferencesManager.getSession() }
                .cast(TunerSession::class.java)
    }
}
