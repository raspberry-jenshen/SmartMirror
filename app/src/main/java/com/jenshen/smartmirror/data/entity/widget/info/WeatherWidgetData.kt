package com.jenshen.smartmirror.data.entity.widget.info

import com.jenshen.smartmirror.data.model.widget.WidgetKey

class WeatherWidgetData(widgetKey: WidgetKey,
                        val hours: Int,
                        val minutes: Int,
                        val seconds: Int) : WidgetData(widgetKey)
