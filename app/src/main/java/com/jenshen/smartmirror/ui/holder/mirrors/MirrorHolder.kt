package com.jenshen.smartmirror.ui.holder.mirrors

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.CompoundButton
import android.widget.LinearLayout
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.firebase.model.mirror.MirrorConfigurationInfo
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.ui.holder.SwipeToDeleteHolder
import kotlinx.android.synthetic.main.item_configuration.view.*
import kotlinx.android.synthetic.main.partial_mirror.view.*
import java.text.SimpleDateFormat
import java.util.*


class MirrorHolder(context: Context, view: View) : SwipeToDeleteHolder(context, view), CompoundButton.OnCheckedChangeListener {

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
                 selectConfigurationClick: (String?, MirrorModel) -> Unit) {
        itemView.deviceInfo.text = mirror.tunerSubscription.deviceInfo
        itemView.addConfiguration_textView.setOnClickListener { addConfigurationClick(mirror) }
        itemView.qr_code_imageView.setOnClickListener { onQrCodeClicked(mirror) }
        if (mirror.mirrorConfigurationsInfo != null) {
            mirror.mirrorConfigurationsInfo!!
                    .toList()
                    .forEach { item: Pair<String, MirrorConfigurationInfo> ->
                        val configurationInfoView = layoutInflater.inflate(R.layout.item_configuration, itemView.configurationContainer, false)
                        with(configurationInfoView) {
                            val configurationKey = item.first
                            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                            configurationsList.add(0, this)
                            itemView.configurationContainer.addView(this)
                            tag = configurationKey
                            titleConfig_textView.text = item.second.title
                            val dateFormat = SimpleDateFormat("h:mm, E, d MMM y", Locale.getDefault())
                            lastUpdate_textView.text = dateFormat.format(Date(item.second.lastTimeUpdate))
                            setOnClickListener { editConfigurationClick(configurationKey, mirror) }
                            checkBox_textView.setOnClickListener {
                                var newConfigurationKey: String? = null
                                if (checkBox_textView.isChecked) {
                                    newConfigurationKey = this!!.tag as String
                                    setCheckedConfiguration(newConfigurationKey)
                                }
                                mirror.checkedConfigurationKey = newConfigurationKey
                                selectConfigurationClick(newConfigurationKey, mirror)
                            }
                            delete_button.setOnClickListener {
                                val view = configurationsList.filter { it.tag == configurationKey }.first()
                                configurationsList.remove(view)
                                (view.parent as ViewGroup).removeView(view)
                                deleteConfigurationClick(configurationKey, mirror)
                            }
                        }
                    }
            setCheckedConfiguration(mirror.checkedConfigurationKey)
        }
        setBackGround()
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {

    }

    private fun setCheckedConfiguration(checkedItemTag: String?) {
        configurationsList.forEach {
            if (it.tag == checkedItemTag) {
                it.checkBox_textView.isChecked = true
            } else {
                it.checkBox_textView.isChecked = false
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