package com.jenshen.smartmirror.data.updater

import com.jenshen.smartmirror.data.entity.widget.info.PhraseWidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import io.reactivex.Flowable
import java.util.*

class PhraseUpdater(widgetKey: WidgetKey) : WidgetUpdater<PhraseWidgetData>(widgetKey) {

    override val initialDelay: Long = 0
    override val period: Long = 1000

    override fun getInfo(): Flowable<PhraseWidgetData> {
        return Flowable.fromCallable {
            val calendar = Calendar.getInstance()
            PhraseWidgetData(widgetKey, "Hello")
        }
    }
}