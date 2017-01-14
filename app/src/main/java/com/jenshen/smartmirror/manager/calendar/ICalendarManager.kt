package com.jenshen.smartmirror.manager.calendar

import android.support.annotation.RequiresPermission
import com.jenshen.smartmirror.data.firebase.model.calendar.CalendarEvent
import com.jenshen.smartmirror.data.firebase.model.calendar.TypesOfUpdaters
import com.jenshen.smartmirror.data.firebase.model.calendar.UserCalendar
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single


interface ICalendarManager {
    @RequiresPermission(android.Manifest.permission.READ_CALENDAR)
    fun getEvents(currentTime: Long): Single<MutableList<CalendarEvent>>

    fun fetchEvents(tunerKey: String): Flowable<UserCalendar>
    fun getEvents(tunerKey: String): Maybe<UserCalendar>
    fun setEvents(tunerKey: String, events: MutableList<CalendarEvent>, @TypesOfUpdaters type: String): Completable
}