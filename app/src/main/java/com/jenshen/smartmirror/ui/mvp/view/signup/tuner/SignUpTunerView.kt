package com.jenshen.smartmirror.ui.mvp.view.signup.tuner

import com.jenshen.compat.base.view.BaseMvpView
import com.jenshen.smartmirror.data.model.UserModel
import com.jenshen.smartmirror.util.validation.ValidationResult

interface SignUpTunerView : BaseMvpView {

    fun setCreateAccountButtonState(isEnabled: Boolean)
    fun onCreateAccountClicked()
    fun onCreateAccountSuccess()

    fun onEmailValidated(result: ValidationResult<String>)
    fun onPasswordValidated(result: ValidationResult<String>)
    fun onUsernameValidated(result: ValidationResult<String>)
    fun onConfirmPasswordValidated(result: ValidationResult<String>)

    fun getModel(): UserModel?
}