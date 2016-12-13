package com.jenshen.smartmirror.data.firebase.model.mirror

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.firebase.database.ServerValue
import com.jenshen.smartmirror.data.firebase.FirebaseConstant
import java.util.*

@IgnoreExtraProperties
data class MirrorConfigurationInfo(@set:PropertyName(FirebaseConstant.Mirror.MirrorConfigurationInfo.TITLE)
                                   @get:PropertyName(FirebaseConstant.Mirror.MirrorConfigurationInfo.TITLE)
                                   var title: String,
                                   @set:PropertyName(FirebaseConstant.Mirror.MirrorConfigurationInfo.LAST_TIME_UPDATE)
                                   @get:PropertyName(FirebaseConstant.Mirror.MirrorConfigurationInfo.LAST_TIME_UPDATE)
                                   var lastTimeUpdate: Long? = null) {
    constructor() : this("", 0L) {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    @Exclude
    fun toValueWithUpdateTime(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put(FirebaseConstant.Mirror.MirrorConfigurationInfo.TITLE, title)
        result.put(FirebaseConstant.Mirror.MirrorConfigurationInfo.LAST_TIME_UPDATE, ServerValue.TIMESTAMP)
        return result
    }
}