package com.jenshen.smartmirror.manager.api.currency

import com.jenshen.smartmirror.data.entity.currency.ExchangeRatesResponse
import com.jenshen.smartmirror.data.entity.weather.day.WeatherForCurrentDayResponse
import com.jenshen.smartmirror.data.entity.weather.forecast.WeatherForecastResponse
import io.reactivex.Single


interface ICurrencyApiManager {
    fun getExchangeRates(): Single<ExchangeRatesResponse>
}