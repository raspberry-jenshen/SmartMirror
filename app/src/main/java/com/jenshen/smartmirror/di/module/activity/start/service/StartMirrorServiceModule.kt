package com.jenshen.smartmirror.di.module.activity.start.service

import com.jenshen.smartmirror.manager.firebase.auth.AuthManager
import com.jenshen.smartmirror.ui.mvp.presenter.start.service.StartMirrorServicePresenter
import dagger.Module


@Module
class StartMirrorServiceModule {

    fun providePresenter(authManager: AuthManager): StartMirrorServicePresenter {
        return StartMirrorServicePresenter(authManager)
    }
}