package com.jenshen.smartmirror.ui.holder.widgets

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetModel
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshen.smartmirror.util.widget.getViewForWidget
import kotlinx.android.synthetic.main.item_widget.view.*


class SimpleWidgetHolder(context: Context,
                         view: View,
                         private val onWidgetUpdate: (WidgetData, Widget<*>) -> Unit) : WidgetHolder(context, view) {

    private val container: LinearLayout

    init {
        container = itemView.widgetsContainer
    }

    override fun bindInfo(model: WidgetModel, onItemClicked: (WidgetModel) -> Unit) {
        super.bindInfo(model, onItemClicked)
        itemView.setOnClickListener { onItemClicked(model) }
        if (widget != null) {
            (widget!!.rootView as ViewGroup).removeView(widget)
        }
        widget = getViewForWidget(model.widgetData.widgetKey.key, context)
        container.addView(widget)
        onWidgetUpdate(model.widgetData, widget as Widget<*>)
    }
}