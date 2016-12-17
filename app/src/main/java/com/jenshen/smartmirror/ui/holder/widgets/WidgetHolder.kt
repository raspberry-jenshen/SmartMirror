package com.jenshen.smartmirror.ui.holder.widgets

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.jenshen.smartmirror.data.model.WidgetModel
import com.jenshen.smartmirror.util.widget.getViewForWidget
import kotlinx.android.synthetic.main.item_widget.view.*


class WidgetHolder : RecyclerView.ViewHolder {

    private val context: Context
    private val name: TextView
    private val container: LinearLayout

    constructor(context: Context, view: View) : super(view) {
        this.context = context
        name = itemView.name_textView
        container = itemView.widgetsContainer
    }

    fun bindInfo(model: WidgetModel) {
        name.text = model.widgetDataSnapshot.data.name

        val widget = getViewForWidget(model.widgetDataSnapshot.key, context)
        if (container.childCount == 0) {
            container.addView(widget)
        } else {
            val view = container.getChildAt(0)
            if (!view.javaClass.isInstance(widget)) {
                (view.parent as ViewGroup).removeView(view)
                container.addView(widget)
            }
        }
    }
}