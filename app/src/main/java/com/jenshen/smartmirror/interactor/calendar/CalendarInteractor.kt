package com.jenshen.smartmirror.interactor.calendar

import com.jenshen.smartmirror.data.firebase.model.calendar.CalendarEvent
import com.jenshen.smartmirror.manager.calendar.ICalendarManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import io.reactivex.Single
import java.util.*

class CalendarInteractor(private val calendarManager: ICalendarManager, private val preferencesManager: PreferencesManager) : ICalendarInteractor {

    override fun getEvents(tunerKey: String?): Single<MutableList<CalendarEvent>> {
        return Single.fromCallable { preferencesManager.isMirror() }
                .flatMap { isMirror ->
                    if (isMirror) {
                        calendarManager.getEvents(tunerKey!!)
                                .map { it.events }
                                .toSingle(mutableListOf<CalendarEvent>())
                    } else {
                        val now = Calendar.getInstance()
                        val start = now.timeInMillis
                        calendarManager.getEvents(start)
                    }
                }
    }
}