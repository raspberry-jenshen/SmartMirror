package com.jenshen.smartmirror.data.entity.widget.info

import com.jenshen.smartmirror.data.firebase.model.calendar.CalendarEvent
import com.jenshen.smartmirror.data.model.widget.WidgetKey


class CalendarEventsWidgetData(widgetKey: WidgetKey, val events: MutableList<CalendarEvent>) : WidgetData(widgetKey)