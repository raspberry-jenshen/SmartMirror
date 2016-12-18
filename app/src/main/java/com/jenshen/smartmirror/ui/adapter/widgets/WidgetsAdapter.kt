package com.jenshen.smartmirror.ui.adapter.widgets

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo
import com.jenshen.smartmirror.data.model.widget.WidgetModel
import com.jenshen.smartmirror.ui.holder.widgets.WidgetHolder
import com.jenshen.smartmirror.ui.view.widget.Widget

class WidgetsAdapter(private val context: Context,
                     private val onItemClicked: (WidgetModel) -> Unit,
                     private val onHolderCreated: (widgetKey: String) -> Unit,
                     private val onWidgetUpdate: (WidgetData, Widget<*>) -> Unit) : RecyclerView.Adapter<WidgetHolder>() {

    override fun getItemCount() = this.itemList.size

    private val itemList: MutableList<WidgetModel>

    companion object {
        const val ITEM_WEATHER = 0
        const val ITEM_CLOCK = 1
    }

    init {
        this.itemList = mutableListOf<WidgetModel>()
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ITEM_WEATHER
            1 -> ITEM_CLOCK
            else -> {
                throw RuntimeException("Can't support this type ")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_widget, parent, false)
        val widgetKey = getWidgetKey(viewType)
        onHolderCreated(widgetKey)
        return WidgetHolder(context, widgetKey, view, onWidgetUpdate)
    }

    override fun onBindViewHolder(holder: WidgetHolder, position: Int) {
        val model = itemList[position]
        holder.itemView.setOnClickListener { onItemClicked(model) }
        holder.bindInfo(model)
    }

    fun addModel(model: WidgetModel) {
        val position = itemList.indexOf(model)
        if (position != -1) {
            itemList.removeAt(position)
            itemList.add(position, model)
            notifyItemChanged(position)
        } else {
            itemList.add(0, model)
            notifyItemInserted(0)
        }
    }

    fun onUpdateItem(widgetData: WidgetData) {
        itemList.forEachIndexed { position, widgetModel ->
            if (widgetModel.widgetDataSnapshot.key == widgetData.widgetKey.key) {
                widgetModel.widgetData = widgetData
                notifyItemChanged(position)
            }
        }
    }

    /* private methods */

    private fun getWidgetKey(type: Int): String {
        return when (type) {
            ITEM_CLOCK -> WidgetInfo.CLOCK_WIDGET_KEY
            ITEM_WEATHER -> WidgetInfo.WEATHER_WIDGET_KEY
            else -> {
                throw RuntimeException("Can't support this widget type")
            }
        }
    }
}