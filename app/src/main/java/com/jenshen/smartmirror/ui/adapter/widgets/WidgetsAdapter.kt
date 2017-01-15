package com.jenshen.smartmirror.ui.adapter.widgets

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import com.jenshen.smartmirror.data.model.widget.WidgetModel
import com.jenshen.smartmirror.ui.holder.widgets.PhraseWidgetHolder
import com.jenshen.smartmirror.ui.holder.widgets.SimpleWidgetHolder
import com.jenshen.smartmirror.ui.holder.widgets.WidgetHolder
import com.jenshen.smartmirror.ui.view.widget.Widget

class WidgetsAdapter(private val context: Context,
                     private val onItemClicked: (WidgetModel) -> Unit,
                     private val onWidgetUpdate: (WidgetData, Widget<*>) -> Unit) : RecyclerView.Adapter<WidgetHolder>() {

    override fun getItemCount() = this.itemList.size

    private val itemList: MutableList<WidgetModel>

    init {
        this.itemList = mutableListOf<WidgetModel>()
    }

    override fun getItemViewType(position: Int): Int {
        val model = itemList[position]
        if (model.widgetData.widgetKey.key == FirebaseRealTimeDatabaseConstant.Widget.PHRASE_WIDGET_KEY) {
            return R.layout.item_widget_phrase
        } else {
            return R.layout.item_widget
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)

        if (viewType == R.layout.item_widget_phrase) {
            return PhraseWidgetHolder(context, view)
        }
        return SimpleWidgetHolder(context, view, onWidgetUpdate)
    }

    override fun onBindViewHolder(holder: WidgetHolder, position: Int) {
        val model = itemList[position]
        holder.bindInfo(model, onItemClicked)
    }

    fun addModel(model: WidgetModel) {
        val position = itemList.indexOf(model)
        if (position != -1) {
            itemList.removeAt(position)
            itemList.add(position, model)
            notifyItemChanged(position)
        } else {
            itemList.add(model)
            notifyItemInserted(itemList.size)
        }
    }

    fun onUpdateItem(widgetData: WidgetData) {
        itemList.forEachIndexed { position, model ->
            if (model.widgetData.widgetKey.key == widgetData.widgetKey.key) {
                model.widgetData = widgetData
                notifyItemChanged(position)
            }
        }
    }
}