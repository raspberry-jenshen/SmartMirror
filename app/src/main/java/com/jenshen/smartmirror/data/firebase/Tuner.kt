package com.jenshen.smartmirror.data.firebase

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class Tuner(@set:PropertyName(FirebaseConstant.Tuner.EMAIL)
                 @get:PropertyName(FirebaseConstant.Tuner.EMAIL)
                 var email: String?) {
    constructor() : this("") {
        // Default constructor required for calls to DataSnapshot.getValue(Mirror.class)
    }
}