package com.jenshen.smartmirror.interactor.calendar

import com.jenshen.smartmirror.data.firebase.model.calendar.CalendarEvent
import io.reactivex.Completable
import io.reactivex.Single

interface ICalendarInteractor {
    fun getEvents(tunerKey: String? = null): Single<MutableList<CalendarEvent>>
    fun updateEvents(): Completable
}