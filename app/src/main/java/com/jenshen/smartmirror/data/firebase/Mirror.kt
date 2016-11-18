package com.jenshen.smartmirror.data.firebase

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Mirror(val macAddress: String) {
    constructor() : this("") {
        // Default constructor required for calls to DataSnapshot.getValue(Mirror.class)
    }
}