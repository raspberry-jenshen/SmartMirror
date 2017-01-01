package com.jenshen.smartmirror.data.entity.weather.day

import com.google.gson.annotations.SerializedName
import com.jenshen.smartmirror.data.entity.weather.Coordinates
import com.jenshen.smartmirror.data.entity.weather.TemperatureConditions
import com.jenshen.smartmirror.data.entity.weather.Weather
import com.jenshen.smartmirror.data.entity.weather.precipitation.Cloud
import com.jenshen.smartmirror.data.entity.weather.precipitation.Rain
import com.jenshen.smartmirror.data.entity.weather.precipitation.Snow
import com.jenshen.smartmirror.data.entity.weather.precipitation.Wind
import java.util.*

class WeatherForCurrentDayResponse(@SerializedName("coord") val coordinates: Coordinates?,
                                   weathersList: MutableList<Weather>?,
                                   temperatureConditions: TemperatureConditions?,
                                   clouds: Cloud,
                                   wind: Wind,
                                   rain: Rain?,
                                   snow: Snow?,
                                   date: Date,
                                   val visibility: Int?,
                                   val sys: Sys?,
                                   @SerializedName("cod") val code: Int?,
                                   val id: String?,
                                   val name: String?) : WeatherForDayResponse(date, temperatureConditions, weathersList, clouds, wind, rain, snow)