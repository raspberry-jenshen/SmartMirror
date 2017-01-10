package com.jenshen.smartmirror.manager.calendar

import android.support.annotation.RequiresPermission
import com.jenshen.smartmirror.data.entity.calendar.CalendarEvent
import io.reactivex.Flowable


interface ICalendarManager {
    @RequiresPermission(android.Manifest.permission.READ_CALENDAR)
    fun getEvents(startTime: Long = -1, endTime: Long = -1): Flowable<CalendarEvent>
}