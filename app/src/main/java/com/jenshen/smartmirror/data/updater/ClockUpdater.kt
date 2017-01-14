package com.jenshen.smartmirror.data.updater

import com.jenshen.smartmirror.data.entity.widget.info.ClockWidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import java.util.Calendar.*
import java.util.concurrent.TimeUnit


class ClockUpdater(widgetKey: WidgetKey) : WidgetUpdater<ClockWidgetData>(widgetKey) {

    override val initialDelay: Long = 0
    override val period: Long = 1000

    override fun getInfo(): Flowable<ClockWidgetData> {
        return Flowable.fromCallable {
            val calendar = Calendar.getInstance()
            ClockWidgetData(widgetKey, calendar.get(HOUR), calendar.get(MINUTE), calendar.get(SECOND))
        }
    }
}