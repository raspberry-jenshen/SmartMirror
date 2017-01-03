package com.jenshen.smartmirror.manager.api.weather

import com.jenshen.smartmirror.data.entity.weather.day.WeatherForCurrentDayResponse
import com.jenshen.smartmirror.data.entity.weather.forecast.WeatherForecastResponse
import io.reactivex.Flowable


interface IWeatherApiManager {

    fun getCurrentWeather(lat: Double, lon: Double): Flowable<WeatherForCurrentDayResponse>
    fun getWeatherForecast(lat: Double, lon: Double): Flowable<WeatherForecastResponse>
}