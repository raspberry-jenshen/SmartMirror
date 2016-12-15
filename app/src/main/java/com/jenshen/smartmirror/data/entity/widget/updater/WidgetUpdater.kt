package com.jenshen.smartmirror.data.entity.widget.updater

import com.jenshen.smartmirror.data.entity.widget.info.InfoForWidget
import com.jenshen.smartmirror.data.entity.widget.info.WidgetKey
import io.reactivex.Observable


abstract class WidgetUpdater<Info : InfoForWidget>(val widgetKey: WidgetKey) {

    protected var isDisposed = false

    abstract fun startUpdate(): Observable<Info>

    fun clear() {

    }
}