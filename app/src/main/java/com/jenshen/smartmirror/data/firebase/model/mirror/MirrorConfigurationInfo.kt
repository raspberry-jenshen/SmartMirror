package com.jenshen.smartmirror.data.firebase.model.mirror

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.firebase.database.ServerValue
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import java.util.*

@IgnoreExtraProperties
data class MirrorConfigurationInfo(@set:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.MirrorConfigurationInfo.TITLE)
                                   @get:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.MirrorConfigurationInfo.TITLE)
                                   var title: String,
                                   @set:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.MirrorConfigurationInfo.LAST_TIME_UPDATE)
                                   @get:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.MirrorConfigurationInfo.LAST_TIME_UPDATE)
                                   var lastTimeUpdate: Long? = null) {
    constructor() : this("", 0L) {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    @Exclude
    fun toValueWithUpdateTime(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put(FirebaseRealTimeDatabaseConstant.Mirror.MirrorConfigurationInfo.TITLE, title)
        result.put(FirebaseRealTimeDatabaseConstant.Mirror.MirrorConfigurationInfo.LAST_TIME_UPDATE, ServerValue.TIMESTAMP)
        return result
    }
}