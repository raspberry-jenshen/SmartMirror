package com.jenshen.smartmirror.util.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import br.tiagohm.clockview.ClockView
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.firebase.FirebaseConstant
import com.jenshensoft.widgetview.WidgetView


fun getViewForWidget(widgetKey: String, context: Context): View {
    val view = when (widgetKey) {
        FirebaseConstant.Widget.CLOCK_WIDGET_KEY -> ClockView(context)
        else -> throw RuntimeException("Can't support this widget")
    }
    view.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    return view
}

fun createWidget(widgetKey: String, context: Context): WidgetView {
    val view = getViewForWidget(widgetKey, context)
    val widgetView = WidgetView(context)
    widgetView.addView(view)
    widgetView.layoutParams = FrameLayout.LayoutParams(context.resources.getDimensionPixelOffset(R.dimen.widget_clock_width),
            context.resources.getDimensionPixelOffset(R.dimen.widget_clock_height))
    return widgetView
}
