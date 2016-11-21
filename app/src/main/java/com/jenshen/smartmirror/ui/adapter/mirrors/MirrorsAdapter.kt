package com.jenshen.smartmirror.ui.adapter.mirrors

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.ui.holder.mirrors.MirrorHolder
import java.util.*

class MirrorsAdapter : RecyclerView.Adapter<MirrorHolder>() {

    private val models: ArrayList<MirrorModel> by lazy {
        return@lazy ArrayList<MirrorModel>()
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: MirrorHolder?, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MirrorHolder {

    }
}