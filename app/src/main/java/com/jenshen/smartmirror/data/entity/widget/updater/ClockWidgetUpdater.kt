package com.jenshen.smartmirror.data.entity.widget.updater

import com.jenshen.smartmirror.data.entity.widget.info.InfoForClockWidget
import com.jenshen.smartmirror.data.entity.widget.info.WidgetKey
import io.reactivex.Observable
import java.util.*
import java.util.Calendar.*
import java.util.concurrent.TimeUnit


class ClockWidgetUpdater(widgetKey: WidgetKey) : WidgetUpdater<InfoForClockWidget>(widgetKey) {

    override fun startUpdate(): Observable<InfoForClockWidget> {

        return Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .takeWhile { !isDisposed }
                .map {
                    val calendar = Calendar.getInstance()
                    InfoForClockWidget(widgetKey, calendar.get(HOUR), calendar.get(MINUTE), calendar.get(SECOND))
                }
    }
}