package com.jenshen.smartmirror.data.entity.weather.day

import com.google.gson.annotations.SerializedName
import com.jenshen.smartmirror.data.entity.weather.TemperatureConditions
import com.jenshen.smartmirror.data.entity.weather.Weather
import com.jenshen.smartmirror.data.entity.weather.precipitation.Cloud
import com.jenshen.smartmirror.data.entity.weather.precipitation.Rain
import com.jenshen.smartmirror.data.entity.weather.precipitation.Snow
import com.jenshen.smartmirror.data.entity.weather.precipitation.Wind
import java.util.*

open class WeatherForDayResponse(@SerializedName("dt") val date: Date,
                                 @SerializedName("main") val temperatureConditions: TemperatureConditions?,
                                 @SerializedName("weather") val weathersList: MutableList<Weather>?,
                                 val clouds: Cloud?,
                                 val wind: Wind?,
                                 @SerializedName("rain") val rain: Rain?,
                                 @SerializedName("snow") val snow: Snow?)