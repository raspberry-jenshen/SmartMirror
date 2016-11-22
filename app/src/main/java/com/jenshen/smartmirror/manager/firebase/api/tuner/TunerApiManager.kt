package com.jenshen.smartmirror.manager.firebase.api.tuner

import com.jenshen.smartmirror.data.firebase.FirebaseChildEvent
import com.jenshen.smartmirror.data.firebase.model.Mirror
import io.reactivex.Completable
import io.reactivex.Flowable


interface TunerApiManager {
    fun addSubscriberToMirror(tunerId: String, mirrorId: String): Completable
    fun removeSubscriberFromMirror(tunerId: String, mirrorId: String): Completable
    fun addSubscriptionToTuner(tunerId: String, mirrorId: String, mirror: Mirror): Completable
    fun removeSubscriptionFromTuner(tunerId: String, mirrorId: String): Completable

    fun setFlagForWaitingSubscribersOnMirror(isWaiting: Boolean, mirrorId: String): Completable
    fun observeTunerSubscriptions(id: String): Flowable<FirebaseChildEvent>
}