package com.jenshen.smartmirror.data.entity.weather

import com.google.gson.annotations.SerializedName

class TemperatureConditions(val temp: Float?,
                            val pressure: Float?,
                            val humidity: Float?,
                            @SerializedName("sea_level")val seaLevel: Float?,
                            @SerializedName("grnd_level")val grndLevel: Float?,
                            @SerializedName("temp_min") val tempMin: Float?,
                            @SerializedName("temp_max") val tempMax: Float?,
                            @SerializedName("temp_kf") val tempKf: Float?)