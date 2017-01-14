package com.jenshen.smartmirror.data.firebase.model.calendar

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.firebase.database.ServerValue
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import java.util.*

@IgnoreExtraProperties
class UserCalendar(@set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.EVENTS)
                   @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.EVENTS)
                   var events: MutableList<CalendarEvent>,
                   @set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.LAST_TIME_UPDATE)
                   @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.LAST_TIME_UPDATE)
                   var lastTimeUpdate: Long? = null) {

    constructor() : this(mutableListOf()) {
        // Default constructor required for calls to DataSnapshot.getValue(Mirror.class)
    }

    @Exclude
    fun toValueWithUpdateTime(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put(FirebaseRealTimeDatabaseConstant.UserCalendar.LAST_TIME_UPDATE, ServerValue.TIMESTAMP)
        result.put(FirebaseRealTimeDatabaseConstant.UserCalendar.EVENTS, events)
        return result
    }
}