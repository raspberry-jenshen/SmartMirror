package com.jenshen.smartmirror.data.firebase.model.tuner

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant

@IgnoreExtraProperties
data class Tuner(@set:PropertyName(FirebaseConstant.Tuner.EMAIL)
                 @get:PropertyName(FirebaseConstant.Tuner.EMAIL)
                 var email: String?) {
    constructor() : this("") {
        // Default constructor required for calls to DataSnapshot.getValue(Mirror.class)
    }
}