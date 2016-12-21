package com.jenshen.smartmirror.data.firebase.model.tuner

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant

@IgnoreExtraProperties
data class TunerInfo(@set:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.TunerInfo.NIKENAME)
                     @get:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.TunerInfo.NIKENAME)
                     var nikeName: String,
                     @set:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.TunerInfo.EMAIL)
                     @get:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.TunerInfo.EMAIL)
                     var email: String,
                     @set:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.TunerInfo.AVATAR_URL)
                     @get:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.TunerInfo.AVATAR_URL)
                     var avatarUrl: String?) {
    constructor() : this("", "", "") {
        // Default constructor required for calls to DataSnapshot.getValue(Mirror.class)
    }
}