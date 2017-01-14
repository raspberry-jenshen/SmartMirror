package com.jenshen.smartmirror.di.module.manager.job

import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.Job
import com.jenshen.smartmirror.di.qualifier.CalendarJob
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.interactor.calendar.ICalendarInteractor
import com.jenshen.smartmirror.manager.job.IJobManager
import com.jenshen.smartmirror.manager.job.JobManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import dagger.Lazy
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class CalendarJobManagerModule {

    @SessionScope
    @Provides
    fun provideCalendarJobManager(calendarInteractor: Lazy<ICalendarInteractor>,
                                  preferencesManager: PreferencesManager,
                                  firebaseJobDispatcher: FirebaseJobDispatcher,
                                  @CalendarJob jobProvider: Provider<Job.Builder>): IJobManager {
        return JobManager(calendarInteractor, preferencesManager, firebaseJobDispatcher, jobProvider)
    }
}
