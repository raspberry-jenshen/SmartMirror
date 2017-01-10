package com.jenshen.smartmirror.data.entity.calendar

import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant

class CalendarEvent(@set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.USER_INFO_KEY)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.USER_INFO_KEY)
                    var userInfoKey: String?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.ID)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.ID)
                    var id: Long?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.TITLE)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.TITLE)
                    var title: String?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.CALENDAR_DISPLAY_NAME)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.CALENDAR_DISPLAY_NAME)
                    var name: String?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.DESCRIPTION)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.DESCRIPTION)
                    var decription: String?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.ALL_DAY)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.ALL_DAY)
                    var isAllDay: Boolean,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.DTSTART)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.DTSTART)
                    var startDate: Long,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.DTEND)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.DTEND)
                    var endDate: Long,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.LAST_DATE)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.LAST_DATE)
                    var lastDate: Long?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.EVENT_LOCATION)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.EVENT_LOCATION)
                    var eventLocation: String?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.EVENT_COLOR)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.EVENT_COLOR)
                    var eventColor: Int?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.CALENDAR_COLOR)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.CALENDAR_COLOR)
                    var calendarColor: Int?,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.Event.ACCOUNT_NAME)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.Event.ACCOUNT_NAME)
                    var accountName: String) : Comparable<CalendarEvent> {

    override fun compareTo(other: CalendarEvent): Int {
        // -1 = less, 0 = equal, 1 = greater
        return startDate.compareTo(other.startDate)
    }

}