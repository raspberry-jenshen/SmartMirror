package com.jenshen.smartmirror.util.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.jenshen.smartmirror.data.firebase.FirebaseConstant
import com.jenshen.smartmirror.ui.view.widget.ClockWidget
import com.jenshensoft.widgetview.WidgetView


fun getViewForWidget(widgetKey: String, context: Context): View {
    val view = when (widgetKey) {
        FirebaseConstant.Widget.CLOCK_WIDGET_KEY -> ClockWidget(context)
        else -> throw RuntimeException("Can't support this widget")
    }
    view.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    return view
}

fun createWidget(widgetKey: String, context: Context): WidgetView {
    val view = getViewForWidget(widgetKey, context)
    val widgetView = WidgetView(context)
    widgetView.addView(view)
    widgetView.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    return widgetView
}
