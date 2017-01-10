package com.jenshen.smartmirror.data.updater

import com.jenshen.smartmirror.data.entity.widget.info.CalendarEventsWidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.manager.calendar.ICalendarManager
import io.reactivex.Flowable
import java.util.*

class CalendarEventsUpdater(widgetKey: WidgetKey,
                            private val calendarManager: ICalendarManager) : WidgetUpdater<CalendarEventsWidgetData>(widgetKey) {

    override val initialDelay: Long = 0
    override val period: Long = MINUTES_BETWEEN_UPDATES * 1000

    companion object {
        const val MINUTES_BETWEEN_UPDATES = 2L * 60L
    }

    override fun getInfo(): Flowable<CalendarEventsWidgetData> {
        val now = Calendar.getInstance()
        val start = now.timeInMillis
        //val end = now.timeInMillis + DateUtils.DAY_IN_MILLIS
        return calendarManager.getEvents(start)
                .toList()
                .map { CalendarEventsWidgetData(widgetKey, it) }
                .toFlowable()
    }
}