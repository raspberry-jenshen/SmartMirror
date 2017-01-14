package com.jenshen.smartmirror.manager.job

import com.jenshen.smartmirror.data.entity.Job
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.interactor.calendar.ICalendarInteractor
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import dagger.Lazy
import io.reactivex.Completable


class JobManager(private val calendarInteractor: Lazy<ICalendarInteractor>,
                 private val preferencesManager: PreferencesManager) : IJobManager {

    override fun onCreateJob(mirrorKey: String,
                             configurationKey: String,
                             currentWidgetKey: String,
                             widgetKey: WidgetKey): Completable {
        if (widgetKey.key == FirebaseRealTimeDatabaseConstant.Widget.CALENDAR_EVENTS_WIDGET_KEY) {
            return calendarInteractor.get().updateEvents()
                    .doOnComplete { preferencesManager.addJob(
                            Job(mirrorKey, configurationKey, currentWidgetKey, widgetKey)) }
        } else {
            return Completable.complete()
        }
    }

    override fun onDeleteJob(mirrorKey: String,
                             configurationKey: String?,
                             currentWidgetKey: String?,
                             widgetKey: WidgetKey?): Completable {
        return Completable.fromCallable {
            preferencesManager.addJob(
                    Job(mirrorKey, configurationKey, currentWidgetKey, widgetKey))
        }
    }
}