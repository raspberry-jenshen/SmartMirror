package com.jenshen.smartmirror.data.entity.weather.forecast

import com.google.gson.annotations.SerializedName
import com.jenshen.smartmirror.data.entity.weather.Coordinates

class City(val id: Int,
           val name: String,
           @SerializedName("coord") val coordinates: Coordinates,
           val country: String,
           val population: Int,
           @SerializedName("sys") val citySys: CitySys)