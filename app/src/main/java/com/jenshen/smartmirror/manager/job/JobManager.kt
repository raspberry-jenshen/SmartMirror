package com.jenshen.smartmirror.manager.job

import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.jenshen.smartmirror.data.entity.Job
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import com.jenshen.smartmirror.data.firebase.model.calendar.AFTER_FIRST_ADD
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.di.qualifier.CalendarJob
import com.jenshen.smartmirror.interactor.calendar.ICalendarInteractor
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Provider


class JobManager(private val calendarInteractor: Lazy<ICalendarInteractor>,
                 private val preferencesManager: PreferencesManager,
                 private val dispatcher: FirebaseJobDispatcher,
                 @CalendarJob
                 private val calendarJobProvider: Provider<com.firebase.jobdispatcher.Job.Builder>) : IJobManager {

    override fun onCreateJob(mirrorKey: String,
                             configurationKey: String,
                             currentWidgetKey: String,
                             widgetKey: WidgetKey): Completable {
        if (widgetKey.key == FirebaseRealTimeDatabaseConstant.Widget.CALENDAR_EVENTS_WIDGET_KEY) {
            return calendarInteractor.get().updateEvents(AFTER_FIRST_ADD)
                    .andThen(Single.fromCallable { Job(mirrorKey, configurationKey, currentWidgetKey, widgetKey) })
                    .doOnSuccess { preferencesManager.addJob(it) }
                    .map { calendarJobProvider.get() }
                    .map { dispatcher.schedule(it.build()) }
                    .doOnSuccess {
                        if (it != FirebaseJobDispatcher.SCHEDULE_RESULT_SUCCESS) {
                            throw  RuntimeException("Can't schedule this job")
                        }
                    }
                    .toCompletable()
        } else {
            return Completable.complete()
        }
    }

    override fun onDeleteJob(mirrorKey: String,
                             configurationKey: String?,
                             currentWidgetKey: String?,
                             widgetKey: WidgetKey?): Completable {
        return Single.fromCallable { Job(mirrorKey, configurationKey, currentWidgetKey, widgetKey) }
                .doOnSuccess { job ->
                    val jobs = preferencesManager.getJobs()
                    if (jobs != null) {
                        if (job.currentWidgetKey != null && job.widgetKey != null) {
                            jobs.filter { it.widgetKey == job.widgetKey }
                                    .toCollection(mutableListOf())
                                    .let {
                                        if (it.size == 1 && it[0] == job) {
                                            dispatcher.cancel(job.widgetKey.key)
                                        }
                                    }

                        } else {
                            if (job.configurationKey == null) {
                                jobs.filter { it.mirrorKey == job.mirrorKey }
                                        .toCollection(mutableListOf())
                                        .forEach { jobToDelete ->
                                            jobs.filter { it.widgetKey == jobToDelete.widgetKey }
                                                    .toCollection(mutableListOf())
                                                    .let {
                                                        if (it.size == 1 && it[0] == job) {
                                                            dispatcher.cancel(jobToDelete.widgetKey!!.key)
                                                        }
                                                    }
                                        }
                            } else {
                                jobs.filter { it.configurationKey == job.configurationKey }
                                        .toCollection(mutableListOf())
                                        .forEach { jobToDelete ->
                                            jobs.filter { it.widgetKey == jobToDelete.widgetKey }
                                                    .toCollection(mutableListOf())
                                                    .let {
                                                        if (it.size == 1 && it[0] == job) {
                                                            dispatcher.cancel(jobToDelete.widgetKey!!.key)
                                                        }
                                                    }
                                        }
                            }
                        }
                    }
                }
                .doOnSuccess { preferencesManager.deleteJob(it) }
                .toCompletable()
    }
}