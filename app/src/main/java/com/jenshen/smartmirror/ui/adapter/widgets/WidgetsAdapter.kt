package com.jenshen.smartmirror.ui.adapter.widgets

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.widget.info.InfoForWidget
import com.jenshen.smartmirror.data.model.WidgetModel
import com.jenshen.smartmirror.ui.holder.widgets.WidgetHolder

class WidgetsAdapter(private val context: Context,
                     private val onItemClicked: (WidgetModel) -> Unit,
                     private val onItemAttached: (position: Int, WidgetModel) -> Unit,
                     private val onItemDetached: (position: Int, WidgetModel) -> Unit) : RecyclerView.Adapter<WidgetHolder>() {

    override fun getItemCount() = this.itemList.size

    private val itemList: MutableList<WidgetModel>

    init {
        this.itemList = mutableListOf<WidgetModel>()
    }

    override fun onViewAttachedToWindow(holder: WidgetHolder) {
        super.onViewAttachedToWindow(holder)
        onItemAttached(holder.layoutPosition, itemList[holder.layoutPosition])
    }

    override fun onViewDetachedFromWindow(holder: WidgetHolder) {
        super.onViewDetachedFromWindow(holder)
        onItemDetached(holder.layoutPosition, itemList[holder.layoutPosition])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_widget, parent, false)
        return WidgetHolder(context, view)
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

    fun onUpdateItem(position: Int, infoForWidget: InfoForWidget) {
        itemList[position].infoForWidget = infoForWidget
        notifyItemChanged(position)
    }
}