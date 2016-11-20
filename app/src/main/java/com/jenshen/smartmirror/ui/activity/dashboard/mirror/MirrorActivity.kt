package com.jenshen.smartmirror.ui.activity.dashboard.mirror

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.di.component.activity.dashboard.mirror.MirrorDashboardComponent
import com.jenshen.smartmirror.ui.mvp.presenter.dashboard.mirror.MirrorDashboardPresenter
import com.jenshen.smartmirror.ui.mvp.view.dashboard.mirror.MirrorDashboardView


class MirrorActivity : BaseDiMvpActivity<MirrorDashboardComponent, MirrorDashboardView, MirrorDashboardPresenter>(), MirrorDashboardView{

    override fun createComponent(): MirrorDashboardComponent {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun injectMembers(instance: MirrorDashboardComponent?) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val manager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = manager.connectionInfo
        val address = info.macAddress
    }
}