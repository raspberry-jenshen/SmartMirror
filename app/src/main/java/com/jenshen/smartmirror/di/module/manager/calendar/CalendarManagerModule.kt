package com.jenshen.smartmirror.di.module.manager.calendar

import android.content.Context
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.calendar.CalendarManager
import com.jenshen.smartmirror.manager.calendar.ICalendarManager
import dagger.Module
import dagger.Provides

@Module
class CalendarManagerModule {

    @SessionScope
    @Provides
    fun provideCalendarManager(context: Context): ICalendarManager {
        return CalendarManager(context)
    }
}
