package com.jenshen.smartmirror.ui.mvp.view.signup

import com.jenshen.compat.base.view.BaseMvpView
import com.jenshen.smartmirror.util.validation.ValidationResult

interface SignUpView : BaseMvpView {

    fun setCreateAccountButtonState(isEnabled: Boolean)
    fun onCreateAccountClicked()
    fun onCreateAccountSuccess()

    fun onEmailValidated(result: ValidationResult<String>)
    fun onPasswordValidated(result: ValidationResult<String>)
    fun onUsernameValidated(result: ValidationResult<String>)
    fun onConfirmPasswordValidated(result: ValidationResult<String>)
}