package com.jenshen.smartmirror.ui.holder.mirrors

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.firebase.model.mirror.MirrorConfigurationInfo
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
                 addConfigurationClick: (MirrorModel) -> Unit,
                 editConfigurationClick: (String, MirrorModel) -> Unit,
                 deleteConfigurationClick: (String, MirrorModel) -> Unit,
                 selectConfigurationClick: (String, MirrorModel) -> Unit) {
        itemView.deviceInfo.text = mirror.tunerSubscription.deviceInfo
        itemView.addConfiguration_textView.setOnClickListener { addConfigurationClick(mirror) }
        itemView.qr_code_imageView.setOnClickListener { onQrCodeClicked(mirror) }
        if (mirror.mirrorConfigurationInfo != null) {
            mirror.mirrorConfigurationInfo!!
                    .toList()
                    .forEach { item: Pair<String, MirrorConfigurationInfo> ->
                        val configurationInfoView = layoutInflater.inflate(R.layout.item_configuration, itemView.configurationContainer)
                        configurationInfoView.tag = mirror.key
                        configurationInfoView.checkBox_textView.setOnCheckedChangeListener { button, isChecked ->
                            if (isChecked) {
                                val newConfigurationId = button!!.tag as String
                                mirror.checkedConfigurationId = newConfigurationId
                                selectConfigurationClick(mirror.key, mirror)
                                setCheckedConfiguration(newConfigurationId)
                            }
                        }
                        configurationInfoView.delete_button.setOnClickListener { deleteConfigurationClick(mirror.key, mirror) }
                        configurationInfoView.setOnClickListener { editConfigurationClick(mirror.key, mirror) }
                        configurationsList.add(0, configurationInfoView)
                    }
        }
        setCheckedConfiguration(mirror.checkedConfigurationId)
        setBackGround()
    }

    private fun setCheckedConfiguration(checkedItemId: String?) {
        configurationsList.forEach {
            if (it.tag == checkedItemId) {
                itemView.checkBox_textView.isChecked = true
            } else {
                itemView.checkBox_textView.isChecked = false
            }
        }
    }

    private fun setBackGround() {
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk > Build.VERSION_CODES.LOLLIPOP) {
            itemView.setBackgroundResource(R.drawable.selector_mirror_item)
        }
    }
}