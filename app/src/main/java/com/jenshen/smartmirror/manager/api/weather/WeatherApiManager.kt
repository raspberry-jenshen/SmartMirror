package com.jenshen.smartmirror.manager.api.weather

import com.jenshen.smartmirror.data.api.weather.WeatherApi
import com.jenshen.smartmirror.data.api.weather.WeatherApi.Companion.METRIC_FORMAT
import com.jenshen.smartmirror.data.entity.weather.day.WeatherForCurrentDayResponse
import com.jenshen.smartmirror.data.entity.weather.forecast.WeatherForecastResponse
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import io.reactivex.Single
import java.util.*



class WeatherApiManager(private val weatherApi: WeatherApi, private val preferencesManager: PreferencesManager) : IWeatherApiManager {

    companion object {
        val MAX_DIFFERENCE = 60 * 60 * 1000
    }

    override fun getCurrentWeather(lat: Double, lon: Double): Single<WeatherForCurrentDayResponse> {
        val currentWeather = preferencesManager.getCurrentWeather()
        if (currentWeather != null) {
            val currentTime = Calendar.getInstance().time.time
            val responseCalculationTime = currentWeather.date.time
            val difference = currentTime - responseCalculationTime
            if (difference <= MAX_DIFFERENCE) {
                return Single.fromCallable { currentWeather }
            }
        }
        return weatherApi.getCurrentWeatherForCity(lat, lon, METRIC_FORMAT, Locale.getDefault().language)
                .doOnSuccess { preferencesManager.saveCurrentWeather(it) }

    }

    override fun getWeatherForecast(lat: Double, lon: Double): Single<WeatherForecastResponse> {
        val weatherForecast = preferencesManager.getWeatherForecast()
        if (weatherForecast != null) {
            val currentTime = Calendar.getInstance().time.time
            val responseCalculationTime = weatherForecast.weathersList.iterator().next().date.time
            val difference = currentTime - responseCalculationTime
            if (difference <= MAX_DIFFERENCE) {
                return Single.fromCallable { weatherForecast }
            }
        }
        return weatherApi.getWeatherForecastForCity(lat, lon, METRIC_FORMAT, Locale.getDefault().language)
                .doOnSuccess { preferencesManager.saveWeatherForecast(it) }
    }
}