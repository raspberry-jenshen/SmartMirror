package com.jenshen.smartmirror.ui.holder.widgets

import android.content.Context
import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.jenshen.smartmirror.data.model.widget.WidgetModel
import kotlinx.android.synthetic.main.item_widget.view.*


abstract class WidgetHolder(protected val context: Context,
                            view: View) : RecyclerView.ViewHolder(view) {

    protected var widget: View? = null
    protected val name: TextView

    init {
        name = itemView.name_textView
    }

    @CallSuper
    open fun bindInfo(model: WidgetModel, onItemClicked: (WidgetModel) -> Unit) {
        name.text = model.widgetInfo.name
    }
}