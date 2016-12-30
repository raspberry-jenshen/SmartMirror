package com.jenshen.smartmirror.di.component.activity.start.splash

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.start.splash.SplashModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.start.splash.SplashActivity
import com.jenshen.smartmirror.ui.mvp.presenter.start.splash.SplashPresenter
import com.jenshen.smartmirror.ui.mvp.view.start.splash.SplashView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(SplashModule::class))
interface SplashComponent : PresenterComponent<SplashView, SplashPresenter>, ActivityComponent<SplashActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<SplashComponent>
}