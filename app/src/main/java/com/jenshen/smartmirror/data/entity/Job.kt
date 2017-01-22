package com.jenshen.smartmirror.data.entity

import com.jenshen.smartmirror.data.model.widget.WidgetKey


data class Job(val mirrorKey: String,
               val configurationKey: String?,
               val currentWidgetKey: String?,
               val widgetKey: WidgetKey?)

