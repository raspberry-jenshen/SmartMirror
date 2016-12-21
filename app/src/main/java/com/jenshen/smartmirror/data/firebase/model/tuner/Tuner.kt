package com.jenshen.smartmirror.data.firebase.model.tuner

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import java.util.*

@IgnoreExtraProperties
data class Tuner(@set:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.TUNER_INFO)
                 @get:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.TUNER_INFO)
                 var tunerInfo: TunerInfo) {

    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.SUBSCRIPTIONS)
    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Tuner.SUBSCRIPTIONS)
    var subscribers: HashMap<String, TunerSubscription>? = null

    constructor() : this(TunerInfo()) {
        // Default constructor required for calls to DataSnapshot.getValue(Mirror.class)
    }
}