package com.jenshen.smartmirror.ui.holder.mirrors

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.ui.holder.SwipeToDeleteHolder
import kotlinx.android.synthetic.main.item_configuration.view.*
import kotlinx.android.synthetic.main.partial_mirror.view.*


class MirrorHolder(context: Context, view: View) : SwipeToDeleteHolder(context, view) {

    private val layoutInflater: LayoutInflater
    private val configurationsList: MutableList<View>

    init {
        layoutInflater = LayoutInflater.from(context)
        configurationsList = mutableListOf()
    }

    override fun onItemSelected() {
        setBackGround()
    }

    override fun onItemClear() {
        setBackGround()
    }

    fun bindInfo(mirror: MirrorModel,
                 onQrCodeClicked: (MirrorModel) -> Unit,
                 deleteConfigurationClick: (MirrorModel) -> Unit,
                 editConfigurationClick: (MirrorModel) -> Unit,
                 addConfigurationClick: (MirrorModel) -> Unit) {
        itemView.deviceInfo.text = mirror.tunerSubscription.deviceInfo
        itemView.addConfiguration_textView.setOnClickListener { addConfigurationClick(mirror) }
        itemView.qr_code_imageView.setOnClickListener { onQrCodeClicked(mirror) }

        if (mirror.mirrorConfigurationInfo != null) {
            mirror.mirrorConfigurationInfo

            for (0...mirror.mirrorConfigurationInfo)


            val configurationInfo = layoutInflater.inflate(R.layout.item_configuration, itemView.configurationContainer)
            configurationInfo.id = mirror.mirrorConfigurationInfo
            configurationInfo.checkBox_textView.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {

                }
            })
            configurationInfo.delete_button.setOnClickListener { deleteConfigurationClick(mirror) }
            configurationInfo.setOnClickListener { editConfigurationClick(mirror) }
            configurationsList.add(0, configurationInfo)
        }
        setBackGround()
    }

    private fun setBackGround() {
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk > Build.VERSION_CODES.LOLLIPOP) {
            itemView.setBackgroundResource(R.drawable.selector_mirror_item)
        }
    }
}