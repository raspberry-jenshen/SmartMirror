package com.jenshen.smartmirror.manager.api.currency

import com.jenshen.smartmirror.BuildConfig
import com.jenshen.smartmirror.data.api.currency.CurrencyApi
import com.jenshen.smartmirror.data.entity.currency.ExchangeRatesResponse
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import io.reactivex.Single
import java.util.*


class CurrencyApiManager(private val currencyApi: CurrencyApi, private val preferencesManager: PreferencesManager) : ICurrencyApiManager {

    companion object {
        val MAX_DIFFERENCE = 60 * 60 * 1000
    }

    override fun getExchangeRates(): Single<ExchangeRatesResponse> {
        val currentWeather = preferencesManager.getExchangeRates()
        if (currentWeather != null) {
            val currentTime = Calendar.getInstance().time.time
            val responseCalculationTime = currentWeather.timestamp.time
            val difference = currentTime - responseCalculationTime
            if (difference <= MAX_DIFFERENCE) {
                return Single.fromCallable { currentWeather }
            }
        }
        return currencyApi.getExchangeRates(BuildConfig.CURRENCY_API_KEY)
                .doOnSuccess { preferencesManager.saveExchangeRates(it) }
    }
}