package com.jenshen.smartmirror.di.component

import com.jenshen.smartmirror.di.module.UserModule
import com.jenshen.smartmirror.di.module.interactor.firebase.api.mirror.MirrorApiInteractorModule
import com.jenshen.smartmirror.di.module.manager.UserSessionModule
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.model.User
import com.jenshen.smartmirror.ui.fragment.SettingsFragment
import dagger.Subcomponent

@SessionScope
@Subcomponent(modules = arrayOf(
        UserModule::class,
        UserSessionModule::class,
        MirrorApiInteractorModule::class))
interface UserComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): UserComponent
    }

    fun provideUser(): User

    fun inject(settingsFragment: SettingsFragment)
}
