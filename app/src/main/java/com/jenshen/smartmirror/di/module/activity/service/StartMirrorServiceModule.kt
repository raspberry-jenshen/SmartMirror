package com.jenshen.smartmirror.di.module.activity.service

import com.jenshen.smartmirror.manager.firebase.auth.AuthManager
import com.jenshen.smartmirror.ui.mvp.presenter.service.StartMirrorServicePresenter
import dagger.Module
import dagger.Provides


@Module
class StartMirrorServiceModule {

    @Provides
    fun providePresenter(authManager: AuthManager): StartMirrorServicePresenter {
        return StartMirrorServicePresenter(authManager)
    }
}