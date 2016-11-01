package com.jenshen.smartmirror.di.component

import com.jenshen.smartmirror.di.module.UserModule
import com.jenshen.smartmirror.di.scope.UserScope
import com.jenshen.smartmirror.model.User
import dagger.Subcomponent

@UserScope
@Subcomponent(modules = arrayOf(UserModule::class))
interface UserComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): UserComponent
    }

    fun provideUser(): User
}
