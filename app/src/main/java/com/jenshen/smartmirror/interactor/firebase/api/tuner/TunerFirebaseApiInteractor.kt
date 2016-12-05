package com.jenshen.smartmirror.interactor.firebase.api.tuner

import android.content.Context
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.firebase.FirebaseChildEvent
import com.jenshen.smartmirror.data.firebase.model.configuration.Corner
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.data.firebase.model.configuration.WidgetConfiguration
import com.jenshen.smartmirror.data.firebase.model.tuner.TunerSubscription
import com.jenshen.smartmirror.data.firebase.model.widget.Size
import com.jenshen.smartmirror.data.firebase.model.widget.Widget
import com.jenshen.smartmirror.data.model.EditMirrorModel
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.data.model.WidgetModel
import com.jenshen.smartmirror.manager.firebase.api.ApiManager
import com.jenshen.smartmirror.manager.firebase.api.tuner.TunerApiManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class TunerFirebaseApiInteractor @Inject constructor(private var context: Context,
                                                     private var apiManager: ApiManager,
                                                     private var preferencesManager: PreferencesManager,
                                                     private var tunerApiManager: TunerApiManager) : TunerApiInteractor {

    /* mirror */

    override fun subscribeOnMirror(mirrorId: String): Completable {
        return apiManager.getMirror(mirrorId)
                .switchIfEmpty { throw RuntimeException(context.getString(R.string.error_cant_find_mirror)) }
                .flatMapCompletable { mirror ->
                    Single.fromCallable { preferencesManager.getSession() }
                            .cast(TunerSession::class.java)
                            .flatMapCompletable {
                                tunerApiManager.addSubscriberToMirror(it.id, mirrorId)
                                        .andThen(tunerApiManager.addSubscriptionToTuner(it.id, mirrorId, mirror))
                            }
                            .andThen(tunerApiManager.setFlagForWaitingSubscribersOnMirror(false, mirrorId))
                }
    }

    override fun unsubscribeFromMirror(mirrorId: String): Completable {
        return apiManager.getMirror(mirrorId)
                .switchIfEmpty { throw RuntimeException(context.getString(R.string.error_cant_find_mirror)) }
                .flatMapCompletable { mirror ->
                    Single.fromCallable { preferencesManager.getSession() }
                            .cast(TunerSession::class.java)
                            .flatMapCompletable {
                                tunerApiManager.removeSubscriberFromMirror(it.id, mirrorId)
                                        .andThen(tunerApiManager.removeSubscriptionFromTuner(it.id, mirrorId))
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

    override fun setConfigurationIdForMirror(configurationId: String, mirrorId: String): Completable {
        return tunerApiManager.setConfigurationIdForMirror(configurationId, mirrorId)
    }

    override fun deleteConfigurationForMirror(configurationId: String, mirrorId: String, isSelected: Boolean): Completable {
        return tunerApiManager.deleteConfigurationForMirror(configurationId)
    }

    /* tuner */

    override fun fetchTunerSubscriptions(): Flowable<MirrorModel> {
        return Single.fromCallable { preferencesManager.getSession() }
                .cast(TunerSession::class.java)
                .flatMapPublisher { tunerApiManager.observeTunerSubscriptions(it.id) }
                .map {
                    MirrorModel(it.dataSnapshot.key,
                            it.dataSnapshot.getValue(TunerSubscription::class.java),
                            it.eventType == FirebaseChildEvent.CHILD_REMOVED)
                }
                .flatMapSingle { model ->
                    tunerApiManager.getMirrorConfigurationsInfo(model.key)
                            .doOnSuccess { model.mirrorConfigurationInfo = it }
                            .isEmpty
                            .map { model }

                    ///todo
                }

    }

    /* widget */

    override fun fetchWidgets(): Flowable<WidgetModel> {
        return tunerApiManager.observeWidgets()
                .map { WidgetModel(it.dataSnapshot.key, it.dataSnapshot.getValue(Widget::class.java)) }
    }

    override fun addWidget(name: String, width: Int, height: Int): Completable {
        return Single.fromCallable { Widget(name, Size(width, height)) }
                .flatMapCompletable { tunerApiManager.addWidget(it) }
    }

    /* mirror configurations */

    override fun addMirrorConfiguration(editMirrorModel: EditMirrorModel): Single<String> {
        val mirrorConfiguration = MirrorConfiguration(editMirrorModel.mirrorId, editMirrorModel.title)
        return tunerApiManager.addMirrorConfiguration(mirrorConfiguration)
                .flatMap{
                    Observable.fromIterable(editMirrorModel.list)
                            .map {
                                WidgetConfiguration(it.widgetKey,
                                        Corner(it.widgetPosition!!.topLeftColumnLine, it.widgetPosition!!.topLeftRowLine),
                                        Corner(it.widgetPosition!!.topRightColumnLine, it.widgetPosition!!.topRightRowLine),
                                        Corner(it.widgetPosition!!.bottomLeftColumnLine, it.widgetPosition!!.bottomLeftRowLine),
                                        Corner(it.widgetPosition!!.bottomRightColumnLine, it.widgetPosition!!.bottomRightRowLine))
                            }
                }
    }

    override fun editConfiguration(key: String, editMirrorModel: EditMirrorModel): Completable {
        return Completable.complete();
    }
}