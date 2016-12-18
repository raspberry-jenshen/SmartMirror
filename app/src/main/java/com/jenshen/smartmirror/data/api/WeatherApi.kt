package com.jenshen.smartmirror.data.api

import com.jenshen.smartmirror.data.entity.weather.CurrentWeatherResponse
import io.reactivex.Single
import retrofit2.http.PUT
import retrofit2.http.Query


interface WeatherApi {

    companion object {
        const val API_URL = "http://api.openweathermap.org/data/2.5/"
        const val IMAGE_PATH_URL = "http://openweathermap.org/img/w/"

        const val METRIC_FORMAT = "metric"
    }

    @PUT("weather")
    fun getCurrentWeatherForCity(@Query("lat") lat: Double,
                                 @Query("lon") lon: Double,
                                 @Query("units") metric: String,
                                 @Query("lang") lang: String): Single<CurrentWeatherResponse>

    @PUT("forecast")
    fun getWeatherForecastForCity(@Query("lat") lat: Double,
                          @Query("lon") lon: Double,
                          @Query("units") metric: String,
                          @Query("lang") lang: String): Single<CurrentWeatherResponse>

}