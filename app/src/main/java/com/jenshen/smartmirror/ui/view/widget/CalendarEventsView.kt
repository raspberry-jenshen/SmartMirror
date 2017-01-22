package com.jenshen.smartmirror.ui.view.widget


import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.firebase.model.calendar.CalendarEvent
import com.jenshen.smartmirror.data.entity.widget.info.CalendarEventsWidgetData
import com.jenshen.smartmirror.util.tintWidget
import com.jenshen.smartmirror.util.toDayMonth
import com.jenshen.smartmirror.util.toHoursMinutesDayMonth
import kotlinx.android.synthetic.main.view_calendar_event.view.*
import java.util.*

class CalendarEventsView : LinearLayout, Widget<CalendarEventsWidgetData> {

    private val listViews: MutableList<View>

    init {
        listViews = mutableListOf()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun updateWidget(widgetData: CalendarEventsWidgetData) {
        val events = widgetData.events.iterator()
        listViews.forEachIndexed { i, view ->
            val calendarEvent: CalendarEvent
            if (events.hasNext()) {
                view.visibility = View.VISIBLE
                calendarEvent = events.next()
                if (calendarEvent.calendarColor != null && calendarEvent.eventColor != 0) {
                    view.tintWidget(calendarEvent.eventColor!!)
                }
                if (calendarEvent.title != null) {
                    view.title.visibility = View.VISIBLE
                    view.title.text = calendarEvent.title
                } else {
                    view.title.visibility = View.INVISIBLE
                }

                if (calendarEvent.decription != null) {
                    view.description.visibility = View.VISIBLE
                    view.description.text = calendarEvent.decription
                } else {
                    view.description.visibility = View.INVISIBLE
                }

                if (calendarEvent.name != null) {
                    view.name.visibility = View.VISIBLE
                    view.name.text = calendarEvent.name
                } else {
                    view.name.visibility = View.INVISIBLE
                }

                if (calendarEvent.eventLocation != null) {
                    view.location.visibility = View.VISIBLE
                    view.location.text = calendarEvent.eventLocation
                } else {
                    view.location.visibility = View.INVISIBLE
                }

                if (calendarEvent.eventLocation != null) {
                    view.location.visibility = View.VISIBLE
                    view.location.text = calendarEvent.eventLocation
                } else {
                    view.location.visibility = View.INVISIBLE
                }

                if (calendarEvent.isAllDay) {
                    view.time.text = "${context.getString(R.string.widget_calendar_events_all_day)}/${Date(calendarEvent.startDate).toDayMonth()}"
                } else {
                    view.time.text = "${Date(calendarEvent.startDate).toHoursMinutesDayMonth()}/${Date(calendarEvent.endDate).toHoursMinutesDayMonth()}"
                }
            } else {
                view.visibility = View.GONE
            }
        }
    }

    /* private methods */

    private fun init() {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        orientation = VERTICAL
        (0..4).forEach {
            val weatherView = LayoutInflater.from(context).inflate(R.layout.view_calendar_event, null)
            weatherView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f)
            addView(weatherView)
            this.listViews.add(weatherView)
        }
    }
}
