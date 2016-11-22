package com.jenshen.smartmirror.ui.adapter.mirrors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.ui.adapter.SwipeToDeleteAdapter
import com.jenshen.smartmirror.ui.holder.mirrors.MirrorHolder

class MirrorsAdapter(private val context: Context,
                     private val onQrCodeClick: (MirrorModel) -> Unit,
                     private val onItemClick: (MirrorModel) -> Unit,
                     onDeleteItemListener: SwipeToDeleteAdapter.OnDeleteItemListener<MirrorModel>) : SwipeToDeleteAdapter<MirrorModel, MirrorHolder>(onDeleteItemListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MirrorHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_mirror, parent, false)
        return MirrorHolder(context, view)
    }

    override fun onBindViewHolder(holder: MirrorHolder, position: Int) {
        val mirror = itemList[position]
        holder.bindInfo(mirror, onQrCodeClick)
        holder.itemView.setOnClickListener { onItemClick(mirror) }
    }

    fun addModel(model: MirrorModel) {
        val position = itemList.indexOf(model)
        if (model.isRemoved) {
            if (position != -1) {
                deleteModel(position)
            }
        } else {
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

    fun deleteModel(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }
}