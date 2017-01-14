package com.jenshen.smartmirror.di.component

import android.app.Activity
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.smartmirror.data.entity.session.Session
import com.jenshen.smartmirror.di.component.activity.service.UpdateCalendarEventsComponent
import com.jenshen.smartmirror.di.module.DispatcherModule
import com.jenshen.smartmirror.di.module.activity.SessionActivityBindingModule
import com.jenshen.smartmirror.di.module.activity.service.UpdateCalendarEventsModule
import com.jenshen.smartmirror.di.module.interactor.firebase.api.mirror.MirrorApiInteractorModule
import com.jenshen.smartmirror.di.module.interactor.firebase.api.tuner.TunerApiInteractorModule
import com.jenshen.smartmirror.di.module.manager.session.SessionModule
import com.jenshen.smartmirror.di.module.manager.session.UserSessionModule
import com.jenshen.smartmirror.di.module.manager.widget.factory.WidgetFactoryManagerModule
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.ui.fragment.settings.app.SettingsFragment
import dagger.Subcomponent

@SessionScope
@Subcomponent(modules = arrayOf(
        SessionModule::class,
        UserSessionModule::class,
        WidgetFactoryManagerModule::class,
        MirrorApiInteractorModule::class,
        TunerApiInteractorModule::class,
        DispatcherModule::class,
        //activities
        SessionActivityBindingModule::class))
interface SessionComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): SessionComponent
    }

    fun provideUser(): Session

    fun inject(settingsFragment: SettingsFragment)

    fun plusCalendarEventsService(moduleUpdate: UpdateCalendarEventsModule) : UpdateCalendarEventsComponent

    @SessionScope
    fun provideMultiBuildersForActivities(): Map<Class<out Activity>, ActivityComponentBuilder<*>>
}
