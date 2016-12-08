package com.jenshen.smartmirror.data.firebase.model.mirror

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.firebase.database.ServerValue
import com.jenshen.smartmirror.data.firebase.FirebaseConstant
import java.util.*


@IgnoreExtraProperties
data class MirrorSubscriber(@set:PropertyName(FirebaseConstant.Mirror.MirrorSubscriber.LAST_TIME_UPDATE)
                            @get:PropertyName(FirebaseConstant.Mirror.MirrorSubscriber.LAST_TIME_UPDATE)
                            var lastTimeUpdate: Long? = null) {

    @Exclude
    fun toValueWithUpdateTime(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put(FirebaseConstant.Mirror.MirrorSubscriber.LAST_TIME_UPDATE, ServerValue.TIMESTAMP)
        return result
    }
}
