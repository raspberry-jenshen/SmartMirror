package com.jenshen.smartmirror.di.module.manager.job

import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.interactor.calendar.ICalendarInteractor
import com.jenshen.smartmirror.manager.job.IJobManager
import com.jenshen.smartmirror.manager.job.JobManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import dagger.Lazy
import dagger.Module
import dagger.Provides

@Module
class CalendarJobManagerModule {

    @SessionScope
    @Provides
    fun provideCalendarJobManager(calendarInteractor: Lazy<ICalendarInteractor>, preferencesManager: PreferencesManager): IJobManager {
        return JobManager(calendarInteractor, preferencesManager)
    }
}
