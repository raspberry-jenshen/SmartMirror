package com.jenshen.smartmirror.di.module

import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.model.User
import dagger.Module
import dagger.Provides

@SessionScope
@Module
class UserModule {

    @Provides
    @SessionScope
    fun provideUser(preferencesManager: PreferencesManager): User {
        return preferencesManager.getUser()!!
    }
}