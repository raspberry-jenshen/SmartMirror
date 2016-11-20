package com.jenshen.smartmirror.di.component

import com.jenshen.smartmirror.data.entity.session.Session
import com.jenshen.smartmirror.di.module.SessionModule
import com.jenshen.smartmirror.di.module.interactor.firebase.api.mirror.MirrorApiInteractorModule
import com.jenshen.smartmirror.di.module.manager.UserSessionModule
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.ui.fragment.SettingsFragment
import dagger.Subcomponent

@SessionScope
@Subcomponent(modules = arrayOf(
        SessionModule::class,
        UserSessionModule::class,
        MirrorApiInteractorModule::class))
interface SessionComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): SessionComponent
    }

    fun provideUser(): Session

    fun inject(settingsFragment: SettingsFragment)
}
