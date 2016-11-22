package com.jenshen.smartmirror.data.firebase.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant
import java.util.*

@IgnoreExtraProperties
data class Mirror(
        @set:PropertyName(FirebaseConstant.Mirror.DEVICE_INFO)
        @get:PropertyName(FirebaseConstant.Mirror.DEVICE_INFO)
        var deviceInfo: String,
        @set:PropertyName(FirebaseConstant.Mirror.IS_WAITING_FOR_TUNER)
        @get:PropertyName(FirebaseConstant.Mirror.IS_WAITING_FOR_TUNER)
        var isWaitingForTuner: Boolean = true) {

    @set:PropertyName(FirebaseConstant.Mirror.SUBSCRIBERS)
    @get:PropertyName(FirebaseConstant.Mirror.SUBSCRIBERS)
    var subscribers: HashMap<String, MirrorSubscriber>? = null

    constructor() : this("") {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(deviceInfo: String, subscribers: HashMap<String, MirrorSubscriber>?) : this(deviceInfo) {
        this.subscribers = subscribers
    }
}