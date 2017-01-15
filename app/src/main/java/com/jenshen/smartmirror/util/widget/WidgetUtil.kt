package com.jenshen.smartmirror.util.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetSize
import com.jenshen.smartmirror.ui.view.widget.*
import com.jenshen.smartmirror.ui.view.widget.weather.CurrentWeatherView
import com.jenshen.smartmirror.ui.view.widget.weather.WeatherForecastForDayView
import com.jenshen.smartmirror.ui.view.widget.weather.WeatherForecastForWeekView
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
        FirebaseRealTimeDatabaseConstant.Widget.CLOCK_WIDGET_KEY -> ClockView(context)
        FirebaseRealTimeDatabaseConstant.Widget.DIGITAL_CLOCK_WIDGET_KEY -> DigitalClockView(context)
        FirebaseRealTimeDatabaseConstant.Widget.CURRENT_WEATHER_WIDGET_KEY -> CurrentWeatherView(context)
        FirebaseRealTimeDatabaseConstant.Widget.WEATHER_FORECAST_FOR_DAY_WIDGET_KEY -> WeatherForecastForDayView(context)
        FirebaseRealTimeDatabaseConstant.Widget.WEATHER_FORECAST_FOR_WEEK_WIDGET_KEY -> WeatherForecastForWeekView(context)
        FirebaseRealTimeDatabaseConstant.Widget.EXCHANGE_RATES_WIDGET_KEY -> ExchangeRatesView(context)
        FirebaseRealTimeDatabaseConstant.Widget.CALENDAR_EVENTS_WIDGET_KEY -> CalendarEventsView(context)
        FirebaseRealTimeDatabaseConstant.Widget.PHRASE_WIDGET_KEY -> PhraseTextView(context)
        else -> ClockView(context)
    }
    view.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    return view
}
