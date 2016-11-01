package com.jenshen.smartmirror.di.module

import com.jenshen.smartmirror.di.scope.UserScope
import com.jenshen.smartmirror.model.User
import dagger.Module
import dagger.Provides
import ua.regin.pocket.manager.preference.PreferencesManager

@UserScope
@Module
class UserModule {

    @Provides
    @UserScope
    fun provideUser(preferencesManager: PreferencesManager): User {
        return preferencesManager.getUser()
    }
}