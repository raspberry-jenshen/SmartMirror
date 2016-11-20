package com.jenshen.smartmirror.data.firebase

import android.support.annotation.IntDef
import com.google.firebase.database.DataSnapshot

class FirebaseChildEvent constructor(val dataSnapshot: DataSnapshot,
                                     @EventType val eventType: Int,
                                     val preViewName: String? = null) {

    companion object {
        const val CHILD_ADDED = 0
        const val CHILD_CHANGED = 1
        const val CHILD_REMOVED = 2
        const val CHILD_MOVED = 3
    }

    @IntDef(CHILD_ADDED.toLong(), CHILD_CHANGED.toLong(), CHILD_REMOVED.toLong(), CHILD_MOVED.toLong())
    @Retention(AnnotationRetention.SOURCE)
    annotation class EventType
}
