package com.jenshen.smartmirror.data.entity.weather.day

import java.util.*


class Sys(val type: Int, val id: Int, val message: Float,
          val country: String, val sunrise: Date, val sunset: Date)