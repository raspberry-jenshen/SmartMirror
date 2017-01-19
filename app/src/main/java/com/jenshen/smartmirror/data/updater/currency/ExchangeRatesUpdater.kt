package com.jenshen.smartmirror.data.updater.currency

import com.jenshen.smartmirror.data.entity.widget.info.currency.ExchangeRatesWidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.data.updater.WidgetUpdater
import com.jenshen.smartmirror.manager.api.currency.ICurrencyApiManager
import io.reactivex.Flowable

class ExchangeRatesUpdater(widgetKey: WidgetKey,
                           private val currencyApiManager: ICurrencyApiManager) : WidgetUpdater<ExchangeRatesWidgetData>(widgetKey) {

    override val initialDelay: Long = 0
    override val period: Long = MINUTES_BETWEEN_UPDATES

    companion object {
        const val MINUTES_BETWEEN_UPDATES = 2L * 60L
    }

    override fun getInfo(): Flowable<ExchangeRatesWidgetData> {
        return currencyApiManager.getExchangeRates()
                .map { ExchangeRatesWidgetData(widgetKey, it) }
    }
}