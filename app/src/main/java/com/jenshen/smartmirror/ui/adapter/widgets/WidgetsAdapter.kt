package com.jenshen.smartmirror.ui.adapter.widgets

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.widget.Widget
import com.jenshen.smartmirror.ui.holder.widgets.WidgetHolder

class WidgetsAdapter(private val onItemClicked: (DataSnapshotWithKey<Widget>) -> Unit) : RecyclerView.Adapter<WidgetHolder>() {

    override fun getItemCount() = this.itemList.size

    private val itemList: MutableList<DataSnapshotWithKey<Widget>>

    init {
        this.itemList = mutableListOf<DataSnapshotWithKey<Widget>>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_widget, parent, false)
        return WidgetHolder(view)
    }

    override fun onBindViewHolder(holder: WidgetHolder, position: Int) {
        val model = itemList[position]
        holder.itemView.setOnClickListener { onItemClicked(model) }
        holder.bindInfo(model)
    }

    fun addModel(model: DataSnapshotWithKey<Widget>) {
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
}