package com.jenshen.smartmirror.manager.api

import com.jenshen.smartmirror.data.entity.weather.WeatherResponse
import io.reactivex.Single


interface IWeatherApiManager {

    fun getWeather(lat: Double, lon: Double): Single<WeatherResponse>
}