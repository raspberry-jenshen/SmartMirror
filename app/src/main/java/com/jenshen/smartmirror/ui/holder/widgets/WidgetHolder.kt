package com.jenshen.smartmirror.ui.holder.widgets

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetModel
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshen.smartmirror.util.widget.getViewForWidget
import kotlinx.android.synthetic.main.item_widget.view.*


class WidgetHolder(private val context: Context,
                   view: View,
                   private val onWidgetUpdate: (WidgetData, Widget<*>) -> Unit) : RecyclerView.ViewHolder(view) {

    private var widget: View? = null
    private val name: TextView
    private val container: LinearLayout

    init {
        name = itemView.name_textView
        container = itemView.widgetsContainer
    }

    fun bindInfo(model: WidgetModel) {
        if (widget != null) {
            (widget!!.rootView as ViewGroup).removeView(widget)
        }
        widget = getViewForWidget(model.widgetData.widgetKey.key, context)
        container.addView(widget)

        name.text = model.widgetInfo.name
        onWidgetUpdate(model.widgetData, widget as Widget<*>)
    }
}