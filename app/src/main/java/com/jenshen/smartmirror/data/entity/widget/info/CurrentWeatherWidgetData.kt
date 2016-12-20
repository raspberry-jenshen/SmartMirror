package com.jenshen.smartmirror.data.entity.widget.info

import com.jenshen.smartmirror.data.entity.weather.day.WeatherForCurrentDayResponse
import com.jenshen.smartmirror.data.model.widget.WidgetKey

class CurrentWeatherWidgetData(widgetKey: WidgetKey, val weatherResponse: WeatherForCurrentDayResponse) : WidgetData(widgetKey)
