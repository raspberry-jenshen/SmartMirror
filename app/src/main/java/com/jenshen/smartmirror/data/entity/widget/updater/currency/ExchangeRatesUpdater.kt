package com.jenshen.smartmirror.data.entity.widget.updater.currency

import com.jenshen.smartmirror.data.entity.widget.info.currency.CurrencyWidgetData
import com.jenshen.smartmirror.data.entity.widget.updater.WidgetUpdater
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.manager.api.currency.ICurrencyApiManager
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ExchangeRatesUpdater(widgetKey: WidgetKey,
                           private val currencyApiManager: ICurrencyApiManager) : WidgetUpdater<CurrencyWidgetData>(widgetKey) {

    companion object {
        const val HOURS_BETWEEN_UPDATES = 2L
    }

    override fun startUpdate(): Observable<CurrencyWidgetData> {

        return Observable.interval(0, HOURS_BETWEEN_UPDATES, TimeUnit.HOURS)
                .takeWhile { !isDisposed }
                .flatMapSingle { currencyApiManager.getExchangeRates() }
                .map { CurrencyWidgetData(widgetKey, it) }
    }
}