package com.jenshen.smartmirror.di.module

import com.jenshen.smartmirror.di.scope.UserScope
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.model.User
import dagger.Module
import dagger.Provides

@UserScope
@Module
class UserModule {

    @Provides
    @UserScope
    fun provideUser(preferencesManager: PreferencesManager): User {
        return preferencesManager.getUser()!!
    }
}