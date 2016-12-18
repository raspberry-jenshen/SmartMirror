package com.jenshen.smartmirror.data.entity.weather

import com.google.gson.annotations.SerializedName
import java.util.*

class CurrentWeatherResponse(val id: String,
                             val name: String,
                             val visibility: Int,
                             @SerializedName("dt") val date: Date,
                             @SerializedName("cod") val code: Int,
                             @SerializedName("coord") val coordinates: Coordinates,
                             @SerializedName("main") val temperatureConditions: TemperatureConditions,
                             @SerializedName("weather") val weathersList: MutableList<Weather>,
                             val clouds: Cloud,
                             val sys: Sys,
                             val wind: Wind)