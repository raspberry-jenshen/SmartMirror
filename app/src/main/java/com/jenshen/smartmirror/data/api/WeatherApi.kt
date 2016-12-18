package com.jenshen.smartmirror.data.api

import com.jenshen.smartmirror.data.entity.WeatherResponse
import io.reactivex.Single
import retrofit2.http.PUT
import retrofit2.http.Query


interface WeatherApi {

    companion object {
        const val API_URL = "api.openweathermap.org/data/2.5/"
    }

    @PUT("weather")
    fun getWeathrForCity(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("APPID") apiKey: String): Single<WeatherResponse>

}