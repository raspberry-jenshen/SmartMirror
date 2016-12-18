package com.jenshen.smartmirror.util.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo
import com.jenshen.smartmirror.ui.view.widget.ClockView
import com.jenshen.smartmirror.ui.view.widget.WeatherView
import com.jenshensoft.widgetview.WidgetView


fun createWidget(widgetKey: String, context: Context): WidgetView {
    val view = getViewForWidget(widgetKey, context)
    val widgetView = WidgetView(context)
    widgetView.addView(view)
    widgetView.layoutParams = FrameLayout.LayoutParams(context.resources.getDimensionPixelOffset(R.dimen.widget_clock_width),
            context.resources.getDimensionPixelOffset(R.dimen.widget_clock_height))
    return widgetView
}

fun getViewForWidget(widgetKey: String, context: Context): View {
    val view: View = when (widgetKey) {
        WidgetInfo.CLOCK_WIDGET_KEY -> ClockView(context)
        WidgetInfo.WEATHER_WIDGET_KEY -> WeatherView(context)
        else -> throw RuntimeException("Can't support this widget")
    }
    view.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    return view
}
