package com.jenshen.smartmirror.interactor.calendar

import com.jenshen.smartmirror.data.firebase.model.calendar.CalendarEvent
import com.jenshen.smartmirror.data.firebase.model.calendar.TypesOfUpdaters
import com.jenshen.smartmirror.manager.calendar.ICalendarManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.*

class CalendarInteractor(private val calendarManager: ICalendarManager, private val preferencesManager: PreferencesManager) : ICalendarInteractor {

    override fun fetchEvents(tunerKey: String?): Flowable<MutableList<CalendarEvent>> {
        return Single.fromCallable { preferencesManager.isMirror() }
                .flatMapPublisher { isMirror ->
                    if (isMirror) {
                        calendarManager.fetchEvents(tunerKey!!)
                                .map { it.events }
                    } else {
                        getEventsFromDb().toFlowable()
                    }
                }
    }

    override fun getEvents(tunerKey: String?): Single<MutableList<CalendarEvent>> {
        return Single.fromCallable { preferencesManager.isMirror() }
                .flatMap { isMirror ->
                    if (isMirror) {
                        calendarManager.getEvents(tunerKey!!)
                                .map { it.events }
                                .toSingle(mutableListOf<CalendarEvent>())
                    } else {
                        getEventsFromDb()
                    }
                }
    }

    override fun updateEvents(@TypesOfUpdaters type: String): Completable {
        return Single.zip(Single.fromCallable { preferencesManager.getSession()!! }
                .map { it.key },
                getEventsFromDb(),
                BiFunction { key: String, events: MutableList<CalendarEvent> ->
                    object {
                        val key = key
                        val events = events
                    }
                })
                .flatMapCompletable { calendarManager.setEvents(it.key, it.events, type) }
    }

    /* private methods */

    private fun getEventsFromDb(): Single<MutableList<CalendarEvent>> {
        val now = Calendar.getInstance()
        val start = now.timeInMillis
        return calendarManager.getEvents(start)
    }
}