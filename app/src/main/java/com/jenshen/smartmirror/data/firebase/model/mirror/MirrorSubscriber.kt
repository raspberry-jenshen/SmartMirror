package com.jenshen.smartmirror.data.firebase.model.mirror

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.firebase.database.ServerValue
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import java.util.*


@IgnoreExtraProperties
data class MirrorSubscriber(@set:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.MirrorSubscriber.LAST_TIME_UPDATE)
                            @get:PropertyName(FirebaseRealTimeDatabaseConstant.Mirror.MirrorSubscriber.LAST_TIME_UPDATE)
                            var lastTimeUpdate: Long? = null) {

    @Exclude
    fun toValueWithUpdateTime(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put(FirebaseRealTimeDatabaseConstant.Mirror.MirrorSubscriber.LAST_TIME_UPDATE, ServerValue.TIMESTAMP)
        return result
    }
}
