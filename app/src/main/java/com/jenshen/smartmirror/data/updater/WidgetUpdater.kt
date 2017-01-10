package com.jenshen.smartmirror.data.updater

import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


abstract class WidgetUpdater<Info : WidgetData>(val widgetKey: WidgetKey) {

    abstract val initialDelay: Long
    abstract val period: Long

    protected var isDisposed = false

    fun startUpdate(): Flowable<Info> {
        return Flowable.interval(initialDelay, period, TimeUnit.MILLISECONDS, Schedulers.io())
                .takeWhile { !isDisposed }
                .flatMap { getInfo() }
    }

    abstract fun getInfo(): Flowable<Info>

    fun clear() {
        isDisposed = true
    }
}