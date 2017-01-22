package com.jenshen.smartmirror.data.updater

import com.jenshen.smartmirror.data.entity.widget.info.CalendarEventsWidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.interactor.calendar.ICalendarInteractor
import io.reactivex.Flowable

class CalendarEventsUpdater(widgetKey: WidgetKey,
                            private val tunerKey: String?,
                            private val calendarInteractor: ICalendarInteractor) : WidgetUpdater<CalendarEventsWidgetData>(widgetKey) {

    override val initialDelay: Long = 0
    override val period: Long = MINUTES_BETWEEN_UPDATES * 60L

    companion object {
        const val MINUTES_BETWEEN_UPDATES = 2L * 60L
    }

    override fun getInfo(): Flowable<CalendarEventsWidgetData> {
        return calendarInteractor.fetchEvents(tunerKey)
                .map { CalendarEventsWidgetData(widgetKey, it) }
    }
}