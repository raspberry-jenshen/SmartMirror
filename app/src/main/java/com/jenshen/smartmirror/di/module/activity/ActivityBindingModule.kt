package com.jenshen.smartmirror.di.module.activity

import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.smartmirror.di.component.activity.signIn.SignInComponent
import com.jenshen.smartmirror.di.component.activity.signUp.mirror.SignUpMirrorComponent
import com.jenshen.smartmirror.di.component.activity.signUp.tuner.SignUpTunerComponent
import com.jenshen.smartmirror.di.component.activity.start.splash.SplashComponent
import com.jenshen.smartmirror.di.multibuildings.ActivityKey
import com.jenshen.smartmirror.ui.activity.signIn.SignInTunerActivity
import com.jenshen.smartmirror.ui.activity.signup.mirror.SignUpMirrorActivity
import com.jenshen.smartmirror.ui.activity.signup.tuner.SignUpTunerActivity
import com.jenshen.smartmirror.ui.activity.splash.SplashActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(subcomponents = arrayOf(
        SplashComponent::class,
        SignInComponent::class,
        SignUpTunerComponent::class,
        SignUpMirrorComponent::class))
abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity::class)
    abstract fun splashActivityComponentBuilder(impl: SplashComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(SignInTunerActivity::class)
    abstract fun sighInActivityComponentBuilder(impl: SignInComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(SignUpTunerActivity::class)
    abstract fun signUpActivityComponentBuilder(impl: SignUpTunerComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(SignUpMirrorActivity::class)
    abstract fun qrCodeActivityComponentBuilder(impl: SignUpMirrorComponent.Builder): ActivityComponentBuilder<*>
}