package com.jenshen.smartmirror.data.firebase.model.mirror

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import java.util.*

@IgnoreExtraProperties
data class Mirror(
        @set:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.DEVICE_INFO)
        @get:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.DEVICE_INFO)
        var deviceInfo: String,
        @set:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.IS_WAITING_FOR_TUNER)
        @get:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.IS_WAITING_FOR_TUNER)
        var isWaitingForTuner: Boolean = true) {

    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.SELECTED_CONFIGURATION_ID)
    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.SELECTED_CONFIGURATION_ID)
    var selectedConfigurationId: String? = null

    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.SUBSCRIBERS)
    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.SUBSCRIBERS)
    var subscribers: HashMap<String, MirrorSubscriber>? = null

    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.CONFIGURATIONS)
    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.CONFIGURATIONS)
    var configurations: HashMap<String, MirrorConfigurationInfo>? = null

    constructor() : this("") {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}