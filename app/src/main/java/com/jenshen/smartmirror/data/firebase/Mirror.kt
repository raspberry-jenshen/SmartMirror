package com.jenshen.smartmirror.data.firebase

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class Mirror(
        @set:PropertyName(FirebaseConstant.Mirror.IS_WAITING_FOR_TUNER)
        @get:PropertyName(FirebaseConstant.Mirror.IS_WAITING_FOR_TUNER)
        var isWaitingForTuner: Boolean = true) {

    @set:PropertyName(FirebaseConstant.Mirror.SUBSCRIBERS)
    @get:PropertyName(FirebaseConstant.Mirror.SUBSCRIBERS)
    private var subscribers: List<MirrorSubscriber>? = null

    constructor(subscribers: List<MirrorSubscriber>?) : this() {
        this.subscribers = subscribers
    }
}