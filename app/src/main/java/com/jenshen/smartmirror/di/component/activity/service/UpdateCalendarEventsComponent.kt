package com.jenshen.smartmirror.di.component.activity.service

import com.jenshen.smartmirror.di.module.activity.service.UpdateCalendarEventsModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.service.UpdateCalendarEventsJobService
import com.jenshen.smartmirror.service.UpdateCalendarEventsService
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = arrayOf(UpdateCalendarEventsModule::class))
interface UpdateCalendarEventsComponent {
    fun injectMembers(service: UpdateCalendarEventsJobService)

    fun injectMembers(service: UpdateCalendarEventsService)
}