package com.jenshen.smartmirror.util.validation

import android.text.TextUtils
import android.util.Patterns
import com.jenshen.smartmirror.R
import io.reactivex.Single

fun isValidEmail(email: String): Single<ValidationResult<String>> {
    return Single.fromCallable { validateEmail(email) }
}

fun isValidPassword(password: String): Single<ValidationResult<String>> {
    return Single.fromCallable { validatePassword(password) }
}

fun validateEmail(email: String): ValidationResult<String> {
    if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return ValidationResult.success(email)
    } else {
        return ValidationResult.failure(R.string.login_error_invalid_email, email)
    }
}

fun validatePassword(password: String): ValidationResult<String> {
    if (password.length > 5) {
        return ValidationResult.success(password)
    } else {
        return ValidationResult.failure(R.string.login_error_invalid_password, password)
    }
}