package com.jenshen.smartmirror.data.entity.weather

import com.google.gson.annotations.SerializedName


class Coordinates(@SerializedName("lon") val lon: Double,
                  @SerializedName("lat") val lat: Double)
