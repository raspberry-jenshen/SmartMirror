package com.jenshen.smartmirror.ui.mvp.view.qrcode

import com.jenshen.compat.base.view.BaseMvpView
import com.jenshen.smartmirror.data.firebase.Mirror

interface QRCodeView : BaseMvpView {
    fun onLoginSuccess()
    fun onMirrorCreated(mirror: Mirror)
}