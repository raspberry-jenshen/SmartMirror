package com.jenshen.smartmirror.data.entity.widget.info

class InfoForClockWidget(widgetKey: WidgetKey,
                         val hours: Int,
                         val minutes: Int,
                         val seconds: Int) : InfoForWidget(widgetKey)
