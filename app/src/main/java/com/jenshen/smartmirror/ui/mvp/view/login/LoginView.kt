package com.jenshen.smartmirror.ui.mvp.view.login

import com.jenshen.compat.base.view.BaseMvpView
import com.jenshen.smartmirror.util.validation.ValidationResult

interface LoginView : BaseMvpView {
    fun onEmailValidated(result: ValidationResult<String>)
    fun onPasswordValidated(result: ValidationResult<String>)
    fun setLoginButtonState(isEnabled: Boolean)
    fun onLoginClicked()
    fun onLoginSuccess()
}