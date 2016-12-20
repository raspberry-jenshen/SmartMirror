package com.jenshen.smartmirror.util.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetSize
import com.jenshen.smartmirror.ui.view.widget.ClockView
import com.jenshen.smartmirror.ui.view.widget.CurrentWeatherView
import com.jenshen.smartmirror.ui.view.widget.WeatherForecastForDayView
import com.jenshen.smartmirror.ui.view.widget.WeatherForecastForWeekView
import com.jenshensoft.widgetview.WidgetView


fun createWidget(widgetKey: String, widgetSize: WidgetSize, context: Context): WidgetView {
    val view = getViewForWidget(widgetKey, context)
    val widgetView = WidgetView(context)
    widgetView.addView(view)
    val offset = context.resources.getDimensionPixelOffset(R.dimen.widget_size)
    widgetView.layoutParams = FrameLayout.LayoutParams(widgetSize.width * offset, widgetSize.height * offset)
    return widgetView
}

fun getViewForWidget(widgetKey: String, context: Context): View {
    val view: View = when (widgetKey) {
        WidgetInfo.CLOCK_WIDGET_KEY -> ClockView(context)
        WidgetInfo.CURRENT_WEATHER_WIDGET_KEY -> CurrentWeatherView(context)
        WidgetInfo.WEATHER_FORECAST_FOR_DAY_WIDGET_KEY -> WeatherForecastForDayView(context)
        WidgetInfo.WEATHER_FORECAST_FOR_WEEK_WIDGET_KEY -> WeatherForecastForWeekView(context)
        else -> throw RuntimeException("Can't support this widget")
    }
    view.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    return view
}
