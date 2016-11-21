package com.jenshen.smartmirror.manager.firebase.api.tuner

import io.reactivex.Completable


interface TunerApiManager {
    fun addSubscriberToMirror(mirrorId: String): Completable
    fun addSubscriptionToTuner(tunerId: String, mirrorId: String): Completable
    fun setFlagForWaitingSubscribersOnMirror(isWaiting: Boolean, mirrorId: String): Completable
}