package com.jenshen.smartmirror.util.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.jenshen.smartmirror.data.firebase.FirebaseConstant
import com.jenshen.smartmirror.data.model.WidgetModel
import com.jenshen.smartmirror.ui.activity.edit.mirror.EditMirrorActivity
import com.jenshen.smartmirror.ui.view.widget.ClockWidget


fun EditMirrorActivity.getWidgetView(widgetModel: WidgetModel, context: Context): View {
    val view = when (widgetModel.id) {
        FirebaseConstant.Widget.CLOCK_WIDGET_KEY -> ClockWidget(context)
        else -> throw RuntimeException("Can't support this widget")
    }
    view.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    return view
}
