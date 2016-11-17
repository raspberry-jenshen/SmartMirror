package com.jenshen.smartmirror.data.firebase

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Mirror(val id: Long,
                  val macAddress: String) {
    constructor() : this(0L, "") {
        // Default constructor required for calls to DataSnapshot.getValue(Mirror.class)
    }
}