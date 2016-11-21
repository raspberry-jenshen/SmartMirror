package com.jenshen.smartmirror.data.firebase

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName


@IgnoreExtraProperties
data class TunerSubscription(@set:PropertyName(FirebaseConstant.Tuner.TunerSubscription.ID)
                            @get:PropertyName(FirebaseConstant.Tuner.TunerSubscription.ID)
                            var id: String) {

}
