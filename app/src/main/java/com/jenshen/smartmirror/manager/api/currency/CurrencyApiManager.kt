package com.jenshen.smartmirror.manager.api.currency

import com.jenshen.smartmirror.BuildConfig
import com.jenshen.smartmirror.data.api.currency.CurrencyApi
import com.jenshen.smartmirror.data.entity.currency.ExchangeRatesResponse
import com.jenshen.smartmirror.data.updater.currency.ExchangeRatesUpdater
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleSource
import java.util.*


class CurrencyApiManager(private val currencyApi: CurrencyApi, private val preferencesManager: PreferencesManager) : ICurrencyApiManager {

    override fun getExchangeRates(): Flowable<ExchangeRatesResponse> {
        val data = preferencesManager.getExchangeRates()
        val singles = mutableListOf<SingleSource<ExchangeRatesResponse>>()
        val dataFromCacheSingle = Single.fromCallable { data!! }
        val dataFromApiSingle = currencyApi.getExchangeRates(BuildConfig.CURRENCY_API_KEY)
                .doOnSuccess { preferencesManager.saveExchangeRates(it) }

        if (data != null) {
            singles.add(dataFromCacheSingle)
            val currentTime = Calendar.getInstance().time.time
            val responseCalculationTime = data.timestamp.time
            val difference = currentTime - responseCalculationTime
            if (difference >= ExchangeRatesUpdater.MINUTES_BETWEEN_UPDATES * 1000) {
                singles.add(dataFromApiSingle)
            }
        } else {
            singles.add(dataFromApiSingle)
        }
        return Single.merge(singles)
    }
}