package com.jenshen.smartmirror.data.firebase

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class Mirror(
        @set:PropertyName(FirebaseConstant.Mirrors.IS_WAITING_FOR_TUNER)
        @get:PropertyName(FirebaseConstant.Mirrors.IS_WAITING_FOR_TUNER)
        var isWaitingForTuner: Boolean = true) {

}