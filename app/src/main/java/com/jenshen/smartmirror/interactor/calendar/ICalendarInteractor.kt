package com.jenshen.smartmirror.interactor.calendar

import com.jenshen.smartmirror.data.firebase.model.calendar.CalendarEvent
import com.jenshen.smartmirror.data.firebase.model.calendar.TypesOfUpdaters
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ICalendarInteractor {
    fun fetchEvents(tunerKey: String? = null): Flowable<MutableList<CalendarEvent>>
    fun getEvents(tunerKey: String? = null): Single<MutableList<CalendarEvent>>
    fun updateEvents(@TypesOfUpdaters type: String): Completable
}