package com.jenshen.smartmirror.data.entity.widget.updater

import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import io.reactivex.Observable


abstract class WidgetUpdater<Info : WidgetData>(val widgetKey: WidgetKey) {

    protected var isDisposed = false

    abstract fun startUpdate(): Observable<Info>

    fun clear() {
        isDisposed = true
    }
}