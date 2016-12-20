package com.jenshen.smartmirror.data.entity.weather

import com.jenshen.smartmirror.data.api.WeatherApi


class Weather(val id: Int, val description: String, val main: String, val icon: String) {
    var iconUrl: String = icon
        get() = WeatherApi.IMAGE_PATH_URL + icon + ".png"
}
