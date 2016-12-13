package com.jenshen.smartmirror.ui.holder.widgets

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.widget.Widget
import kotlinx.android.synthetic.main.partial_widget.view.*


class WidgetHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindInfo(model: DataSnapshotWithKey<Widget>) {
        itemView.name_textView.text = model.data.name
        setBackGround()
    }

    private fun setBackGround() {
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk > Build.VERSION_CODES.LOLLIPOP) {
            itemView.setBackgroundResource(R.drawable.selector_mirror_item)
        }
    }
}