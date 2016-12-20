package com.jenshen.smartmirror.data.entity.widget.info.weather

import com.jenshen.smartmirror.data.entity.weather.forecast.WeatherForecastResponse
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey

class WeatherForecastWidgetData(widgetKey: WidgetKey, val weatherResponse: WeatherForecastResponse) : WidgetData(widgetKey)
