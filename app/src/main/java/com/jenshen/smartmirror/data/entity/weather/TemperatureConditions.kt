package com.jenshen.smartmirror.data.entity.weather

import com.google.gson.annotations.SerializedName

class TemperatureConditions(val temp: Float,
                            val pressure: String,
                            val humidity: String,
                            @SerializedName("temp_min") val tempMin: Float,
                            @SerializedName("temp_max") val tempMax: Float)