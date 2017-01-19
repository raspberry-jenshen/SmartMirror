package com.jenshen.smartmirror.data.updater

import com.jenshen.smartmirror.data.entity.widget.info.PhraseWidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import io.reactivex.Flowable

class PhraseUpdater(widgetKey: WidgetKey, private val phrase: String? = null) : WidgetUpdater<PhraseWidgetData>(widgetKey) {

    override val initialDelay: Long = 0
    override val period: Long = -1

    override fun getInfo(): Flowable<PhraseWidgetData> {
        return Flowable.fromCallable { PhraseWidgetData(widgetKey, phrase ?: "") }
    }
}