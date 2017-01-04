package com.jenshen.smartmirror.data.entity.widget.updater.currency

import com.jenshen.smartmirror.data.entity.widget.info.currency.ExchangeRatesWidgetData
import com.jenshen.smartmirror.data.entity.widget.updater.WidgetUpdater
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.manager.api.currency.ICurrencyApiManager
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ExchangeRatesUpdater(widgetKey: WidgetKey,
                           private val currencyApiManager: ICurrencyApiManager) : WidgetUpdater<ExchangeRatesWidgetData>(widgetKey) {

    companion object {
        const val MINUTES_BETWEEN_UPDATES = 2L * 60L
    }

    override fun startUpdate(): Flowable<ExchangeRatesWidgetData> {

        return Flowable.interval(0, MINUTES_BETWEEN_UPDATES, TimeUnit.MINUTES)
                .takeWhile { !isDisposed }
                .flatMap { currencyApiManager.getExchangeRates() }
                .map { ExchangeRatesWidgetData(widgetKey, it) }
    }
}