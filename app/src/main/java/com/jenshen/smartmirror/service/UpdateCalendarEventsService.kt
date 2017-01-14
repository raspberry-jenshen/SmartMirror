package com.jenshen.smartmirror.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.firebase.model.calendar.UPDATE_CALENDAR_RECEIVER
import com.jenshen.smartmirror.di.module.activity.service.UpdateCalendarEventsModule
import com.jenshen.smartmirror.ui.mvp.presenter.service.UpdateCalendarEventsPresenter
import com.jenshen.smartmirror.ui.mvp.view.service.UpdateCalendarEventsView
import javax.inject.Inject

class UpdateCalendarEventsService : Service(), UpdateCalendarEventsView {

    @Inject
    protected lateinit var presenter: UpdateCalendarEventsPresenter

    /* lifecycle */

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        presenter.onStartJob(UPDATE_CALENDAR_RECEIVER)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        SmartMirrorApp.userComponent!!
                .plusCalendarEventsService(UpdateCalendarEventsModule())
                .injectMembers(this)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(false)
    }

    /* callbacks */

    override fun onJobCompleted() {
        stopSelf()
    }

    /* base */

    override fun handleError(throwable: Throwable?) {
        Log.e(context.getString(R.string.app_name), throwable.toString())
        Crashlytics.logException(throwable)
    }

    override fun getContext() = this

    override fun hideProgress() {
        throw UnsupportedOperationException("not implemented")
    }

    override fun showProgress() {
        throw UnsupportedOperationException("not implemented")
    }
}