package com.jenshen.smartmirror.ui.mvp.view.qrcode

import com.jenshen.compat.base.view.BaseMvpView
import com.jenshen.smartmirror.model.User
import com.jenshen.smartmirror.util.validation.ValidationResult

interface QRCodeView : BaseMvpView {
    fun onLoginSuccess()
}