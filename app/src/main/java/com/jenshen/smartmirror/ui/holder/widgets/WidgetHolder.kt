package com.jenshen.smartmirror.ui.holder.widgets

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo
import com.jenshen.smartmirror.util.widget.getViewForWidget
import kotlinx.android.synthetic.main.item_widget.view.*


class WidgetHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    fun bindInfo(model: DataSnapshotWithKey<WidgetInfo>) {
        itemView.name_textView.text = model.data.name
        val widget = getViewForWidget(model.key, context)
        itemView.widgetsContainer.addView(widget)
    }
}