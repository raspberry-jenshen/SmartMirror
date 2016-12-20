package com.jenshen.smartmirror.manager.api

import com.jenshen.smartmirror.data.api.WeatherApi
import com.jenshen.smartmirror.data.api.WeatherApi.Companion.METRIC_FORMAT
import com.jenshen.smartmirror.data.entity.weather.day.WeatherForCurrentDayResponse
import com.jenshen.smartmirror.data.entity.weather.forecast.WeatherForecastResponse
import io.reactivex.Single
import java.util.*


class WeatherApiManager(private val weatherApi: WeatherApi) : IWeatherApiManager {

    override fun getCurrentWeather(lat: Double, lon: Double): Single<WeatherForCurrentDayResponse> {
        return weatherApi.getCurrentWeatherForCity(lat, lon, METRIC_FORMAT, Locale.getDefault().language)
    }

    override fun getWeatherForecast(lat: Double, lon: Double): Single<WeatherForecastResponse> {
        return weatherApi.getWeatherForecastForCity(lat, lon, METRIC_FORMAT, Locale.getDefault().language)
    }
}