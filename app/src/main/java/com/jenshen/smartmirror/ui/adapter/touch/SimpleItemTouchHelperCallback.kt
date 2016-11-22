/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jenshen.smartmirror.ui.adapter.touch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.IntDef
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.jenshen.smartmirror.R

class SimpleItemTouchHelperCallback(private val helperAdapter: ItemTouchHelperAdapter,
                                    private val context: Context,
                                    @Flags swipeFlags: Int?) : ItemTouchHelper.Callback() {
    private var swipeFlags: Int = 0
    private var background: Drawable? = null
    private var xMark: Drawable? = null
    private var xMarkMargin: Int = 0
    private var initiated: Boolean = false

    init {
        if (swipeFlags == null) {
            this.swipeFlags = 0
        } else {
            this.swipeFlags = swipeFlags
        }
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return ItemTouchHelper.Callback.makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, source: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return source.itemViewType == target.itemViewType
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
        // Notify the adapter of the dismissal
        helperAdapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView

            // not sure why, but this method get's called for viewholder that are already swiped away
            if (viewHolder.adapterPosition == -1) {
                // not interested in those
                return
            }

            if (!initiated) {
                init()
            }

            // draw red background
            background!!.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            background!!.draw(c)

            // draw x mark
            val itemHeight = itemView.bottom - itemView.top
            val intrinsicWidth = xMark!!.intrinsicWidth
            val intrinsicHeight = xMark!!.intrinsicWidth

            val xMarkLeft = itemView.right - xMarkMargin - intrinsicWidth
            val xMarkRight = itemView.right - xMarkMargin
            val xMarkTop = itemView.top + (itemHeight - intrinsicHeight) / 2
            val xMarkBottom = xMarkTop + intrinsicHeight
            xMark!!.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom)

            xMark!!.draw(c)

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        // We only want the active item to change
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is ItemTouchHelperViewHolder) {
                // Let the view holder know that this item is being moved or dragged
                viewHolder.onItemSelected()
            }
        }

        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        viewHolder.itemView.alpha = ALPHA_FULL

        if (viewHolder is ItemTouchHelperViewHolder) {
            // Tell the view holder it's time to restore the idle state
            viewHolder.onItemClear()
        }
    }

    private fun init() {
        background = ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent))
        xMark = ContextCompat.getDrawable(context, R.drawable.ic_delete)
        xMark!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        xMarkMargin = context.resources.getDimension(R.dimen.offset_big1).toInt()
        initiated = true
    }

    @IntDef(ItemTouchHelper.UP.toLong(), ItemTouchHelper.DOWN.toLong(), ItemTouchHelper.LEFT.toLong(), ItemTouchHelper.RIGHT.toLong())
    @Retention(AnnotationRetention.SOURCE)
    annotation class Flags

    companion object {

        val ALPHA_FULL = 1.0f
    }
}
