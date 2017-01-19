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
        val emitter : Flowable<Long>
        if (period == -1L) {
            emitter = Flowable.timer(initialDelay, TimeUnit.MILLISECONDS, Schedulers.io())
        } else {
            emitter = Flowable.interval(initialDelay, period * 100L * 1000L, TimeUnit.MILLISECONDS, Schedulers.io())
        }
        return emitter
                .takeWhile { !isDisposed }
                .flatMap { getInfo() }
    }

    abstract fun getInfo(): Flowable<Info>

    fun clear() {
        isDisposed = true
    }
}