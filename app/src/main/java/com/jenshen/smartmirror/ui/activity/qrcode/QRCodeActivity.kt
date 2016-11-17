package com.jenshen.smartmirror.ui.activity.qrcode

import android.os.Bundle
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.firebase.Mirror
import com.jenshen.smartmirror.di.component.activity.qrcode.QRCodeComponent
import com.jenshen.smartmirror.ui.mvp.presenter.qrcode.QRCodePresenter
import com.jenshen.smartmirror.ui.mvp.view.qrcode.QRCodeView


class QRCodeActivity : BaseDiMvpActivity<QRCodeComponent, QRCodeView, QRCodePresenter>(), QRCodeView {


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

    /* callbacks */

    override fun onLoginSuccess() {
        presenter.signInMirror()
    }

    override fun onMirrorCreated(mirror: Mirror) {

    }
}