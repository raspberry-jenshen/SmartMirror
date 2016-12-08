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
import com.jenshen.smartmirror.data.firebase.model.widget.Widget
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

    override fun addSubscriberToMirror(tunerId: String, mirrorId: String): Completable {
        return fireBaseDatabase
                .getMirrorSubscribersRef(mirrorId)
                .map { it.child(tunerId) }
                .flatMapCompletable { it.uploadValue(MirrorSubscriber().toValueWithUpdateTime()) }
    }

    override fun removeSubscriberFromMirror(tunerId: String, mirrorId: String): Completable {
        return fireBaseDatabase
                .getMirrorSubscribersRef(mirrorId)
                .flatMapCompletable { it.clearValue() }
    }

    override fun setFlagForWaitingSubscribersOnMirror(isWaiting: Boolean, mirrorId: String): Completable {
        return fireBaseDatabase
                .getIsWaitingForTunerFlagRef(mirrorId)
                .flatMapCompletable { it.uploadValue(isWaiting) }
    }

    override fun getMirrorConfigurationsInfo(mirrorId: String): Maybe<HashMap<String, MirrorConfigurationInfo>> {
        return fireBaseDatabase
                .getMirrorConfigurationsInfoRef(mirrorId)
                .flatMap { it.loadValue() }
                .filter { it.exists() }
                .map {
                    val type = object : GenericTypeIndicator<HashMap<String, MirrorConfigurationInfo>>() {}
                    it.getValue(type)
                }
    }

    override fun setConfigurationIdForMirror(configurationId: String, mirrorId: String): Completable {
        return fireBaseDatabase
                .getSelectedConfigurationRef(mirrorId)
                .flatMapCompletable { it.uploadValue(configurationId) }
    }

    override fun createOrEditMirrorConfigurationInfoForMirror(mirrorKey: String, configurationKey: String, configurationInfo: MirrorConfigurationInfo): Completable {
        return fireBaseDatabase.getMirrorConfigurationsInfoRef(mirrorKey)
                .map { it.child(configurationKey) }
                .flatMapCompletable { it.uploadValue(configurationInfo) }
    }

    override fun deleteMirrorConfigurationInfoForMirror(configurationId: String, mirrorId: String): Completable {
        return fireBaseDatabase
                .getMirrorConfigurationInfoRef(configurationId, mirrorId)
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
                .flatMapCompletable { it.uploadValue( ServerValue.TIMESTAMP) }
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

    override fun getWidget(widgetKey: String): Single<DataSnapshotWithKey<Widget>> {
        return fireBaseDatabase
                .getWidgetRef(widgetKey)
                .flatMap { it.loadValue() }
                .map { DataSnapshotWithKey(it.key, it.getValue(Widget::class.java)) }
    }

    override fun addWidget(widget: Widget): Single<String> {
        return fireBaseDatabase
                .getWidgetsRef()
                .map { it.push() }
                .flatMap {
                    it.uploadValue(Widget)
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

    override fun deleteMirrorConfiguration(configurationId: String): Completable {
        return fireBaseDatabase.getMirrorConfigurationRef(configurationId)
                .flatMapCompletable { it.clearValue() }
    }
}
