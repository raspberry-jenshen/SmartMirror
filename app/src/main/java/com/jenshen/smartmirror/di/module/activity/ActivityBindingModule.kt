package com.jenshen.smartmirror.di.module.activity

import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.smartmirror.di.component.activity.signIn.SignInComponent
import com.jenshen.smartmirror.di.component.activity.signUp.SignUpComponent
import com.jenshen.smartmirror.di.component.activity.splash.SplashComponent
import com.jenshen.smartmirror.di.multibuildings.ActivityKey
import com.jenshen.smartmirror.ui.activity.signIn.SignInActivity
import com.jenshen.smartmirror.ui.activity.signUp.SignUpActivity
import com.jenshen.smartmirror.ui.activity.splash.SplashActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(subcomponents = arrayOf(
        SplashComponent::class,
        SignInComponent::class,
        SignUpComponent::class))
abstract class  ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity::class)
    abstract fun splashActivityComponentBuilder(impl: SplashComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(SignInActivity::class)
    abstract fun sighInActivityComponentBuilder(impl: SignInComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(SignUpActivity::class)
    abstract fun signUpActivityComponentBuilder(impl: SignUpComponent.Builder): ActivityComponentBuilder<*>
}