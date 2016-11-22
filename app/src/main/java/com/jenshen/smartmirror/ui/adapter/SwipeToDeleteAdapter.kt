package com.jenshen.smartmirror.ui.adapter


import android.support.v7.widget.RecyclerView
import com.jenshen.smartmirror.ui.adapter.touch.ItemTouchHelperAdapter
import com.jenshen.smartmirror.ui.holder.SwipeToDeleteHolder

abstract class SwipeToDeleteAdapter<Item, Holder : SwipeToDeleteHolder>(
        protected val onDeleteItemListener: SwipeToDeleteAdapter.OnDeleteItemListener<Item>?): RecyclerView.Adapter<Holder>(), ItemTouchHelperAdapter {

    protected val itemList: MutableList<Item>

    init {
        this.itemList = arrayListOf<Item>()
    }

    override fun onItemDismiss(position: Int) {
        if (onDeleteItemListener != null) {
            val item = itemList[position]
            onDeleteItemListener.onDeleteItem(position, item)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    /* inner types */

    interface OnDeleteItemListener<in T> {
        fun onDeleteItem(position: Int, item: T)
    }
}