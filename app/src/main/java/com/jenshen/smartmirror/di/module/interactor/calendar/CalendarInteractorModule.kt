package com.jenshen.smartmirror.di.module.interactor.calendar

import com.jenshen.smartmirror.di.module.manager.calendar.CalendarManagerModule
import com.jenshen.smartmirror.interactor.calendar.CalendarInteractor
import com.jenshen.smartmirror.interactor.calendar.ICalendarInteractor
import com.jenshen.smartmirror.manager.calendar.ICalendarManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(CalendarManagerModule::class))
class CalendarInteractorModule {

    @Provides
    fun provideCalendarInteractor(preferencesManager: PreferencesManager, calendarManager: ICalendarManager): ICalendarInteractor {
        return CalendarInteractor(calendarManager, preferencesManager)
    }
}