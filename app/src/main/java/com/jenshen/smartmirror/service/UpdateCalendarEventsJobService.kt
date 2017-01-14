package com.jenshen.smartmirror.service

import android.util.Log
import com.crashlytics.android.Crashlytics
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.firebase.model.calendar.JOB_DISPATCHER
import com.jenshen.smartmirror.di.module.activity.service.UpdateCalendarEventsModule
import com.jenshen.smartmirror.ui.mvp.presenter.service.UpdateCalendarEventsPresenter
import com.jenshen.smartmirror.ui.mvp.view.service.UpdateCalendarEventsView
import javax.inject.Inject

class UpdateCalendarEventsJobService : JobService(), UpdateCalendarEventsView {

    @Inject
    protected lateinit var presenter: UpdateCalendarEventsPresenter

    private var isJobCompleted = false

    /* lifecycle */

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

    override fun onStartJob(job: JobParameters): Boolean {
        isJobCompleted = false
        presenter.onStartJob(JOB_DISPATCHER)

        return isJobCompleted // Answers the question: "Is there still work going on?"
    }

    override fun onStopJob(job: JobParameters): Boolean {
        return isJobCompleted // Answers the question: "Should this job be retried?"
    }

    /* callbacks */

    override fun onJobCompleted() {
        isJobCompleted = true
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