package com.jenshen.smartmirror.data.firebase

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.firebase.database.ServerValue
import java.util.*


@IgnoreExtraProperties
data class MirrorSubscriber(@set:PropertyName(FirebaseConstant.Mirror.MirrorSubscriber.ID)
                            @get:PropertyName(FirebaseConstant.Mirror.MirrorSubscriber.ID)
                            var id: String) {

    @set:PropertyName(FirebaseConstant.Mirror.MirrorSubscriber.LAST_TIME_UPDATE)
    @get:PropertyName(FirebaseConstant.Mirror.MirrorSubscriber.LAST_TIME_UPDATE)
    var lastTimeUpdate: String? = null

    constructor(id: String, lastTimeUpdate: String) : this(id) {
        this.lastTimeUpdate = lastTimeUpdate
    }

    @Exclude
    fun toValueWithUpdateTime(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put(FirebaseConstant.Mirror.MirrorSubscriber.LAST_TIME_UPDATE, ServerValue.TIMESTAMP)
        return result
    }
}
