package com.jenshen.smartmirror.di.component.activity.splash

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.splash.SplashModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.splash.SplashActivity
import com.jenshen.smartmirror.ui.mvp.presenter.splash.SplashPresenter
import com.jenshen.smartmirror.ui.mvp.view.splash.SplashView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(SplashModule::class))
interface SplashComponent : PresenterComponent<SplashView, SplashPresenter>, ActivityComponent<SplashActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<SplashComponent>
}