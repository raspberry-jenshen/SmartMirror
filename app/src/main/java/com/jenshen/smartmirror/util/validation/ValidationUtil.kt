package com.jenshen.smartmirror.util.validation

import android.text.TextUtils
import android.util.Patterns
import com.jenshen.smartmirror.R
import io.reactivex.Single

fun isValidUserName(userName: String): Single<ValidationResult<String>> {
    return Single.fromCallable { validateUserName(userName) }
}

fun isValidEmail(email: String): Single<ValidationResult<String>> {
    return Single.fromCallable { validateEmail(email) }
}

fun isValidPassword(password: String): Single<ValidationResult<String>> {
    return Single.fromCallable { validatePassword(password) }
}

fun isValidConfirmPassword(password: String, confirmPassword :String): Single<ValidationResult<String>> {
    return Single.fromCallable { validateConfirmPassword(password, confirmPassword) }
}

fun validateUserName(name: String): ValidationResult<String> {
    if (!TextUtils.isEmpty(name) && name.length > 3) {
        return ValidationResult.success(name)
    } else {
        return ValidationResult.failure(R.string.error_invalid_username, name)
    }
}

fun validateEmail(email: String): ValidationResult<String> {
    if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return ValidationResult.success(email)
    } else {
        return ValidationResult.failure(R.string.error_invalid_email, email)
    }
}

fun validatePassword(password: String): ValidationResult<String> {
    if (password.length > 5) {
        return ValidationResult.success(password)
    } else {
        return ValidationResult.failure(R.string.error_invalid_password, password)
    }
}

fun validateConfirmPassword(password: String, confirmPassword :String): ValidationResult<String> {
    if (password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword) {
        return ValidationResult.success(confirmPassword)
    } else {
        return ValidationResult.failure(R.string.error_invalid_confirm_password, confirmPassword)
    }
}