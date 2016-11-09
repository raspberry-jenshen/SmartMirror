package com.jenshen.smartmirror.di.module.activity

import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.smartmirror.di.component.activity.login.LoginComponent
import com.jenshen.smartmirror.di.component.activity.splash.SplashComponent
import com.jenshen.smartmirror.di.multibuildings.ActivityKey
import com.jenshen.smartmirror.ui.activity.login.LoginActivity
import com.jenshen.smartmirror.ui.activity.splash.SplashActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(subcomponents = arrayOf(
        SplashComponent::class,
        LoginComponent::class))
abstract class  ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity::class)
    abstract fun splashActivityComponentBuilder(impl: SplashComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(LoginActivity::class)
    abstract fun loginActivityComponentBuilder(impl: LoginComponent.Builder): ActivityComponentBuilder<*>
}