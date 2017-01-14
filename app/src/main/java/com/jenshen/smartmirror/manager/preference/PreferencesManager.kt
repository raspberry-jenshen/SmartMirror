package com.jenshen.smartmirror.manager.preference

import com.jenshen.smartmirror.data.entity.Job
import com.jenshen.smartmirror.data.entity.currency.ExchangeRatesResponse
import com.jenshen.smartmirror.data.entity.session.Session
import com.jenshen.smartmirror.data.entity.weather.day.WeatherForCurrentDayResponse
import com.jenshen.smartmirror.data.entity.weather.forecast.WeatherForecastResponse
import io.reactivex.Completable

interface PreferencesManager {

    fun sighIn(session: Session, isMirror: Boolean)

    fun logout(): Completable

    fun getSession(): Session?

    fun isMirror(): Boolean

    fun saveCurrentWeather(weatherForCurrentDayResponse: WeatherForCurrentDayResponse)

    fun saveWeatherForecast(weatherForecastResponse: WeatherForecastResponse)

    fun saveExchangeRates(exchangeRatesResponse: ExchangeRatesResponse)

    fun getCurrentWeather(): WeatherForCurrentDayResponse?

    fun getWeatherForecast(): WeatherForecastResponse?

    fun getExchangeRates(): ExchangeRatesResponse?

    fun addJob(job: Job)

    fun getJobs() : MutableList<Job>?

    fun deleteJob(job: Job)
}