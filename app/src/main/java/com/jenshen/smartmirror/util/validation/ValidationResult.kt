package com.jenshen.smartmirror.util.validation

import android.support.annotation.StringRes

class ValidationResult<out T> private constructor(val isValid: Boolean, @StringRes val reasonStringRes: Int = 0, val data: T) {

    companion object {

        fun <T> success(t: T): ValidationResult<T> {
            return ValidationResult(true, data = t)
        }

        fun <T> failure(@StringRes reason: Int, t: T): ValidationResult<T> {
            return ValidationResult(false, reason, t)
        }
    }
}