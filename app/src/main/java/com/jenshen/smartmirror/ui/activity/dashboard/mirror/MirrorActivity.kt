package com.jenshen.smartmirror.ui.activity.dashboard.mirror

import android.content.Context
import android.os.Bundle
import com.jenshen.compat.base.view.impl.BaseActivity
import android.net.wifi.WifiManager
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiInfo



class MirrorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val manager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = manager.connectionInfo
        val address = info.macAddress
    }
}