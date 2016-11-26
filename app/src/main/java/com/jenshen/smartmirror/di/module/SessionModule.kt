package com.jenshen.smartmirror.di.module

import com.jenshen.smartmirror.data.entity.session.Session
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import dagger.Module
import dagger.Provides

@SessionScope
@Module
class SessionModule {

    @Provides
    @SessionScope
    fun provideUser(preferencesManager: PreferencesManager): Session {
        return preferencesManager.getSession()!!
    }
}