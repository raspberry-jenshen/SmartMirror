package com.jenshen.smartmirror.data.firebase

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class Tuner(@set:PropertyName(FirebaseConstant.Tuners.EMAIL)
                 @get:PropertyName(FirebaseConstant.Tuners.EMAIL)
                 var email: String?) {
    constructor() : this("") {
        // Default constructor required for calls to DataSnapshot.getValue(Mirror.class)
    }
}