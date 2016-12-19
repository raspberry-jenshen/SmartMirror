package com.jenshen.smartmirror.data.entity.weather.forecast

import com.google.gson.annotations.SerializedName
import com.jenshen.smartmirror.data.entity.weather.day.WeatherForDayResponse

class WeatherForecastResponse(val city: City?,
                              val massage: String?,
                              val cnt: Int?,
                              @SerializedName("list") val weathersList: MutableList<WeatherForDayResponse>?)