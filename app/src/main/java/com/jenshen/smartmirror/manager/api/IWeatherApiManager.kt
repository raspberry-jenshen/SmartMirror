package com.jenshen.smartmirror.manager.api

import com.jenshen.smartmirror.data.entity.weather.CurrentWeatherResponse
import io.reactivex.Single


interface IWeatherApiManager {

    fun getCurrentWeather(lat: Double, lon: Double): Single<CurrentWeatherResponse>
    fun getWeatherForecast(lat: Double, lon: Double): Single<CurrentWeatherResponse>
}