package com.jenshen.smartmirror.manager.firebase.api.tuner

import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ServerValue
import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.FirebaseChildEvent
import com.jenshen.smartmirror.data.firebase.FirebaseConstant
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.data.firebase.model.configuration.WidgetConfiguration
import com.jenshen.smartmirror.data.firebase.model.mirror.Mirror
import com.jenshen.smartmirror.data.firebase.model.mirror.MirrorConfigurationInfo
import com.jenshen.smartmirror.data.firebase.model.mirror.MirrorSubscriber
import com.jenshen.smartmirror.data.firebase.model.tuner.TunerSubscription
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo
import com.jenshen.smartmirror.manager.firebase.database.RealtimeDatabaseManager
import com.jenshen.smartmirror.util.reactive.firebase.clearValue
import com.jenshen.smartmirror.util.reactive.firebase.loadValue
import com.jenshen.smartmirror.util.reactive.firebase.observeChildren
import com.jenshen.smartmirror.util.reactive.firebase.uploadValue
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*
import javax.inject.Inject


class TunerFirebaseApiManager @Inject constructor(private val fireBaseDatabase: RealtimeDatabaseManager) : TunerApiManager {


    /* mirror */

    override fun addSubscriberToMirror(tunerKey: String, mirrorKey: String): Completable {
        return fireBaseDatabase
                .getMirrorSubscribersRef(mirrorKey)
                .map { it.child(tunerKey) }
                .flatMapCompletable { it.uploadValue(MirrorSubscriber().toValueWithUpdateTime()) }
    }

    override fun removeSubscriberFromMirror(tunerKey: String, mirrorKey: String): Completable {
        return fireBaseDatabase
                .getMirrorSubscribersRef(mirrorKey)
                .flatMapCompletable { it.clearValue() }
    }

    override fun setFlagForWaitingSubscribersOnMirror(isWaiting: Boolean, mirrorKey: String): Completable {
        return fireBaseDatabase
                .getIsWaitingForTunerFlagRef(mirrorKey)
                .flatMapCompletable { it.uploadValue(isWaiting) }
    }

    override fun getMirrorConfigurationsInfo(mirrorKey: String): Maybe<HashMap<String, MirrorConfigurationInfo>> {
        return fireBaseDatabase
                .getMirrorConfigurationsInfoRef(mirrorKey)
                .flatMap { it.loadValue() }
                .filter { it.exists() }
                .map {
                    val type = object : GenericTypeIndicator<HashMap<String, MirrorConfigurationInfo>>() {}
                    it.getValue(type)
                }
    }

    override fun getSelectedConfigurationKeyForMirror(mirrorKey: String): Maybe<String> {
        return fireBaseDatabase
                .getSelectedConfigurationRef(mirrorKey)
                .flatMap { it.loadValue() }
                .filter { it.exists() }
                .map { it.getValue(String::class.java) }
    }

    override fun setSelectedConfigurationKeyForMirror(configurationKey: String, mirrorKey: String): Completable {
        return fireBaseDatabase
                .getSelectedConfigurationRef(mirrorKey)
                .flatMapCompletable { it.uploadValue(configurationKey) }
    }

    override fun deleteSelectedConfigurationKeyForMirror(mirrorKey: String): Completable {
        return fireBaseDatabase
                .getSelectedConfigurationRef(mirrorKey)
                .flatMapCompletable { it.clearValue() }
    }

    override fun createOrEditMirrorConfigurationInfoForMirror(configurationKey: String, mirrorKey: String, configurationInfo: MirrorConfigurationInfo): Completable {
        return fireBaseDatabase.getMirrorConfigurationsInfoRef(mirrorKey)
                .map { it.child(configurationKey) }
                .flatMapCompletable { it.uploadValue(configurationInfo.toValueWithUpdateTime()) }
    }

    override fun deleteMirrorConfigurationInfoForMirror(configurationKey: String, mirrorId: String): Completable {
        return fireBaseDatabase
                .getMirrorConfigurationInfoRef(configurationKey, mirrorId)
                .flatMapCompletable { it.clearValue() }
    }

    /* tuner */

    override fun addSubscriptionInTuner(tunerId: String, mirrorId: String, mirror: Mirror): Completable {
        return fireBaseDatabase
                .getTunerSubscriptionRef(mirrorId, tunerId)
                .flatMapCompletable { it.uploadValue(TunerSubscription(mirror.deviceInfo).toValueWithUpdateTime()) }
    }

    override fun updateSubscriptionInTuner(tunerKey: String, mirrorKey: String): Completable {
        return fireBaseDatabase
                .getTunerSubscriptionRef(mirrorKey, tunerKey)
                .map { it.child(FirebaseConstant.Tuner.TunerSubscription.LAST_TIME_UPDATE) }
                .flatMapCompletable { it.uploadValue(ServerValue.TIMESTAMP) }
    }

    override fun removeSubscriptionFromTuner(tunerId: String, mirrorId: String): Completable {
        return fireBaseDatabase
                .getTunerSubscriptionsRef(tunerId)
                .map { it.child(mirrorId) }
                .flatMapCompletable { it.clearValue() }
    }

    override fun observeTunerSubscriptions(tunerId: String): Flowable<FirebaseChildEvent> {
        return fireBaseDatabase
                .getTunerSubscriptionsRef(tunerId)
                .flatMapPublisher { it.observeChildren() }
                .filter { it.dataSnapshot.exists() }
    }

    /* widgets */

    override fun observeWidgets(): Flowable<FirebaseChildEvent> {
        return fireBaseDatabase
                .getWidgetsRef()
                .flatMapPublisher { it.observeChildren() }
                .filter { it.dataSnapshot.exists() }
    }

    override fun getWidget(widgetKey: String): Single<DataSnapshotWithKey<WidgetInfo>> {
        return fireBaseDatabase
                .getWidgetRef(widgetKey)
                .flatMap { it.loadValue() }
                .map { DataSnapshotWithKey(it.key, it.getValue(WidgetInfo::class.java)) }
    }

    override fun addWidget(widgetInfo: WidgetInfo): Single<String> {
        return fireBaseDatabase
                .getWidgetsRef()
                .map { it.push() }
                .flatMap {
                    it.uploadValue(widgetInfo)
                            .toSingle { it.key }
                }
    }

    /* mirror configuration */

    override fun createMirrorConfiguration(mirrorConfiguration: MirrorConfiguration): Single<String> {
        return fireBaseDatabase
                .getMirrorsConfigurationsRef()
                .map { it.push() }
                .flatMap {
                    it.uploadValue(mirrorConfiguration)
                            .toSingle { it.key }
                }
    }

    override fun editMirrorConfiguration(configurationsKey: String, mirrorConfiguration: MirrorConfiguration): Completable {
        return fireBaseDatabase
                .getMirrorConfigurationRef(configurationsKey)
                .flatMapCompletable { it.uploadValue(mirrorConfiguration) }
    }

    override fun deleteMirrorConfiguration(configurationId: String): Completable {
        return fireBaseDatabase.getMirrorConfigurationRef(configurationId)
                .flatMapCompletable { it.clearValue() }
    }

    override fun createWidgetInConfiguration(configurationsKey: String, widgetConfiguration: WidgetConfiguration): Single<String> {
        return fireBaseDatabase
                .getMirrorConfigurationWidgetsRef(configurationsKey)
                .map { it.push() }
                .flatMap {
                    it.uploadValue(widgetConfiguration)
                            .toSingle { it.key }
                }
    }

    override fun editWidgetInConfiguration(configurationsKey: String, keyWidget: String, widgetConfiguration: WidgetConfiguration): Completable {
        return fireBaseDatabase
                .getMirrorConfigurationWidgetRef(keyWidget, configurationsKey)
                .flatMapCompletable { it.uploadValue(widgetConfiguration) }
    }

    override fun deleteWidgetInConfiguration(configurationsKey: String, keyWidget: String) :Completable {
        return fireBaseDatabase
                .getMirrorConfigurationWidgetRef(keyWidget, configurationsKey)
                .flatMapCompletable { it.clearValue() }
    }
}
