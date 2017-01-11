package com.jenshen.smartmirror.data.firebase.model.calendar

import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant


class UserCalendar(@set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.EVENTS)
                   @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.EVENTS)
                   var events: MutableList<CalendarEvent>) {

    constructor() : this(mutableListOf()) {
        // Default constructor required for calls to DataSnapshot.getValue(Mirror.class)
    }
}