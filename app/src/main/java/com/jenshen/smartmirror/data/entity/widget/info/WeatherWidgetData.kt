package com.jenshen.smartmirror.data.entity.widget.info

import com.jenshen.smartmirror.data.entity.weather.WeatherResponse
import com.jenshen.smartmirror.data.model.widget.WidgetKey

class WeatherWidgetData(widgetKey: WidgetKey,
                        val iconUrl: String,
                        val weatherResponse: WeatherResponse) : WidgetData(widgetKey)
