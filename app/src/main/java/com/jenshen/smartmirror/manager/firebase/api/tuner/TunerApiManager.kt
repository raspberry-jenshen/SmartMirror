package com.jenshen.smartmirror.manager.firebase.api.tuner

import com.jenshen.smartmirror.data.firebase.FirebaseChildEvent
import com.jenshen.smartmirror.data.firebase.model.mirror.Mirror
import com.jenshen.smartmirror.data.firebase.model.mirror.MirrorConfigurationInfo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import java.util.*


interface TunerApiManager {

    /* mirror */
    fun addSubscriberToMirror(tunerId: String, mirrorId: String): Completable
    fun removeSubscriberFromMirror(tunerId: String, mirrorId: String): Completable
    fun setFlagForWaitingSubscribersOnMirror(isWaiting: Boolean, mirrorId: String): Completable
    fun getMirrorConfigurationsInfo(mirrorId: String): Maybe<HashMap<String, MirrorConfigurationInfo>>
    fun setConfigurationIdForMirror(configurationId: String, mirrorId: String): Completable
    fun deleteConfigurationForMirror(configurationId: String): Completable

    /* tuner */
    fun addSubscriptionToTuner(tunerId: String, mirrorId: String, mirror: Mirror): Completable
    fun removeSubscriptionFromTuner(tunerId: String, mirrorId: String): Completable
    fun observeTunerSubscriptions(tunerId: String): Flowable<FirebaseChildEvent>
    fun observeWidgets(): Flowable<FirebaseChildEvent>
}