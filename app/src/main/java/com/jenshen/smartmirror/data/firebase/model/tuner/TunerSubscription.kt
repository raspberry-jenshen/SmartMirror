package com.jenshen.smartmirror.data.firebase.model.tuner

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.firebase.database.ServerValue
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import java.util.*


@IgnoreExtraProperties
data class TunerSubscription(@set:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.TunerSubscription.DEVICE_INFO)
                             @get:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.TunerSubscription.DEVICE_INFO)
                             var deviceInfo: String,
                             @set:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.TunerSubscription.LAST_TIME_UPDATE)
                             @get:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.TunerSubscription.LAST_TIME_UPDATE)
                             var lastTimeUpdate: Long? = null) {

    constructor() : this("") {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    @Exclude
    fun toValueWithUpdateTime(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put(FirebaseRealTimeDatabaseConstant.Tuner.TunerSubscription.DEVICE_INFO, deviceInfo)
        result.put(FirebaseRealTimeDatabaseConstant.Tuner.TunerSubscription.LAST_TIME_UPDATE, ServerValue.TIMESTAMP)
        return result
    }
}
