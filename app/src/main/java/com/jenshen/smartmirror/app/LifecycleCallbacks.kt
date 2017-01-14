package com.jenshen.smartmirror.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.jenshen.compat.util.delegate.HasDelegateView

class LifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityStarted(activity: Activity?) {

    }

    override fun onActivityDestroyed(activity: Activity?) {
        if (activity is HasDelegateView<*>) {
            activity.viewDelegate = null
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity?) {

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is HasDelegateView<*>) {
            activity.viewDelegate = ActivityDelegate(activity)
        }
    }

    override fun onActivityResumed(activity: Activity?) {

    }
}