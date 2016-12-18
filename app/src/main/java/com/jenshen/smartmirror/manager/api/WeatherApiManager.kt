package com.jenshen.smartmirror.manager.api

import com.jenshen.smartmirror.data.api.WeatherApi
import com.jenshen.smartmirror.data.api.WeatherApi.Companion.METRIC_FORMAT
import com.jenshen.smartmirror.data.entity.weather.WeatherResponse
import io.reactivex.Single
import java.util.*


class WeatherApiManager(private val weatherApi: WeatherApi) : IWeatherApiManager {

    override fun getWeather(lat: Double, lon: Double): Single<WeatherResponse> {
        return weatherApi.getWeatherForCity(lat, lon, METRIC_FORMAT, Locale.getDefault().language)
    }
}