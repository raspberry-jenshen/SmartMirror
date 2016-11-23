package com.jenshen.smartmirror.data.firebase.model.tuner

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant


@IgnoreExtraProperties
data class TunerSubscription(@set:PropertyName(FirebaseConstant.Tuner.TunerSubscription.ID)
                             @get:PropertyName(FirebaseConstant.Tuner.TunerSubscription.ID)
                             var id: String,
                             @set:PropertyName(FirebaseConstant.Tuner.TunerSubscription.DEVICE_INFO)
                             @get:PropertyName(FirebaseConstant.Tuner.TunerSubscription.DEVICE_INFO)
                             var deviceInfo: String) {

    constructor() : this("", "") {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}
