package com.jenshen.smartmirror.di.module.activity.service

import com.jenshen.smartmirror.interactor.calendar.ICalendarInteractor
import com.jenshen.smartmirror.ui.mvp.presenter.service.UpdateCalendarEventsPresenter
import dagger.Module
import dagger.Provides


@Module
class UpdateCalendarEventsModule {

    @Provides
    fun providePresenter(calendarInteractor: ICalendarInteractor): UpdateCalendarEventsPresenter {
        return UpdateCalendarEventsPresenter(calendarInteractor)
    }
}