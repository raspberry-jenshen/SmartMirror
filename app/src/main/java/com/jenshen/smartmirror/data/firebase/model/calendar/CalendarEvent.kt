package com.jenshen.smartmirror.data.firebase.model.calendar

import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant

class CalendarEvent(@set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.ID)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.ID)
                    var id: Long,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.TITLE)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.TITLE)
                    var title: String?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.CALENDAR_DISPLAY_NAME)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.CALENDAR_DISPLAY_NAME)
                    var name: String?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.DESCRIPTION)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.DESCRIPTION)
                    var decription: String?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.ALL_DAY)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.ALL_DAY)
                    var isAllDay: Boolean,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.DTSTART)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.DTSTART)
                    var startDate: Long,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.DTEND)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.DTEND)
                    var endDate: Long,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.LAST_DATE)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.LAST_DATE)
                    var lastDate: Long?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.EVENT_LOCATION)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.EVENT_LOCATION)
                    var eventLocation: String?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.EVENT_COLOR)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.EVENT_COLOR)
                    var eventColor: Int?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.CALENDAR_COLOR)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.CALENDAR_COLOR)
                    var calendarColor: Int?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.ACCOUNT_NAME)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.UserCalendar.Event.ACCOUNT_NAME)
                    var accountName: String) : Comparable<CalendarEvent> {

    constructor() : this(0L, null, null, null, false, 0L, 0L, null, null, null, null, "") {
        // Default constructor required for calls to DataSnapshot.getValue(Mirror.class)
    }

    override fun compareTo(other: CalendarEvent): Int {
        // -1 = less, 0 = equal, 1 = greater
        return startDate.compareTo(other.startDate)
    }

}