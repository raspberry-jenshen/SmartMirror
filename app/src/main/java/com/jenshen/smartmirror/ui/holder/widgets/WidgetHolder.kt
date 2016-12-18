package com.jenshen.smartmirror.ui.holder.widgets

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetModel
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshen.smartmirror.util.widget.getViewForWidget
import kotlinx.android.synthetic.main.item_widget.view.*


class WidgetHolder(context: Context,
                   widgetKey: String,
                   view: View,
                   private val onWidgetUpdate: (WidgetData, Widget<*>) -> Unit) : RecyclerView.ViewHolder(view) {

    private val widget: View
    private val name: TextView
    private val container: LinearLayout

    init {
        name = itemView.name_textView
        container = itemView.widgetsContainer
        widget = getViewForWidget(widgetKey, context)
        container.addView(widget)
    }

    fun bindInfo(model: WidgetModel) {
        name.text = model.widgetDataSnapshot.data.name
        if (model.widgetData != null) {
            onWidgetUpdate(model.widgetData!!, widget as Widget<*>)
        }
    }
}