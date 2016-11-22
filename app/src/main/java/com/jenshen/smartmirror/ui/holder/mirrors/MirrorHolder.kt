package com.jenshen.smartmirror.ui.holder.mirrors

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.ui.holder.SwipeToDeleteHolder

class MirrorHolder : SwipeToDeleteHolder {

    private val deviceInfo: TextView
    private val qrCodeImage: ImageView


    constructor(context: Context, view: View) : super(context, view) {
        deviceInfo = itemView.findViewById(R.id.deviceInfo) as TextView
        qrCodeImage = itemView.findViewById(R.id.qr_code_imageView) as ImageView
    }

    override fun onItemSelected() {
        setBackGround()
    }

    override fun onItemClear() {
        setBackGround()
    }

    fun bindInfo(mirror: MirrorModel, onQrCodeClicked: (MirrorModel) -> Unit) {
        deviceInfo.text = mirror.tunerSubscription.deviceInfo
        qrCodeImage.setOnClickListener{onQrCodeClicked(mirror)}
        setBackGround()
    }

    private fun setBackGround() {
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk > Build.VERSION_CODES.LOLLIPOP) {
            itemView.setBackgroundResource(R.drawable.selector_mirror_item)
        }
    }
}