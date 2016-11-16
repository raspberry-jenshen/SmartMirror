package com.jenshen.smartmirror.ui.activity.qrcode

import android.content.Context
import android.os.Bundle
import com.jenshen.compat.base.view.impl.BaseActivity
import android.net.wifi.WifiManager
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiInfo
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.component.activity.qrcode.QRCodeComponent
import com.jenshen.smartmirror.di.component.activity.signIn.SignInComponent
import com.jenshen.smartmirror.ui.activity.signIn.SignInActivity
import com.jenshen.smartmirror.ui.mvp.presenter.qrcode.QRCodePresenter
import com.jenshen.smartmirror.ui.mvp.view.qrcode.QRCodeView


class QRCodeActivity : BaseDiMvpActivity<QRCodeComponent, QRCodeView, QRCodePresenter>() {

    /* inject */

    override fun createComponent(): QRCodeComponent {
        return SmartMirrorApp
                .activityComponentBuilders[QRCodeActivity::class.java]?.build() as QRCodeComponent
    }

    override fun injectMembers(instance: QRCodeComponent) {
        instance.injectMembers(this)
    }


    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.signInMirror()

    }
}