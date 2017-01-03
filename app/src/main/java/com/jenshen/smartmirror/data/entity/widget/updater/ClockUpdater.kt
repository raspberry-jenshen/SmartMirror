package com.jenshen.smartmirror.data.entity.widget.updater

import com.jenshen.smartmirror.data.entity.widget.info.ClockWidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.*
import java.util.Calendar.*
import java.util.concurrent.TimeUnit


class ClockUpdater(widgetKey: WidgetKey) : WidgetUpdater<ClockWidgetData>(widgetKey) {

    override fun startUpdate(): Flowable<ClockWidgetData> {
        return Flowable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .takeWhile { !isDisposed }
                .map {
                    val calendar = Calendar.getInstance()
                    ClockWidgetData(widgetKey, calendar.get(HOUR), calendar.get(MINUTE), calendar.get(SECOND))
                }
    }
}