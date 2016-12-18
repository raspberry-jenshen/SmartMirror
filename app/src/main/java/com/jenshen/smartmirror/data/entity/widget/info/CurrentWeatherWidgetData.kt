package com.jenshen.smartmirror.data.entity.widget.info

import com.jenshen.smartmirror.data.entity.weather.CurrentWeatherResponse
import com.jenshen.smartmirror.data.model.widget.WidgetKey

class CurrentWeatherWidgetData(widgetKey: WidgetKey,
                               val iconUrl: String,
                               val weatherResponse: CurrentWeatherResponse) : WidgetData(widgetKey)
