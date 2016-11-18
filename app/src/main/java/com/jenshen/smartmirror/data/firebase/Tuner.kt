package com.jenshen.smartmirror.data.firebase

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Tuner(val email: String?) {
    constructor() : this("") {
        // Default constructor required for calls to DataSnapshot.getValue(Mirror.class)
    }
}