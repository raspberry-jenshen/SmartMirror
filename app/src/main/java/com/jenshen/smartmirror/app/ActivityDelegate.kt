package com.jenshen.smartmirror.app

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.jenshen.compat.util.delegate.ViewDelegateActivity


class ActivityDelegate(context : Context) : ViewDelegateActivity(context) {

    override fun handleError(throwable: Throwable?) {
        Crashlytics.logException(throwable)
        super.handleError(throwable)
    }
}