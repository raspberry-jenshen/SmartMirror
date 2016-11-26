package com.jenshen.smartmirror.di.component.activity.signUp.tuner

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.signUp.tuner.SignUpTunerModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.signup.tuner.SignUpTunerActivity
import com.jenshen.smartmirror.ui.mvp.presenter.signup.tuner.SignUpTunerPresenter
import com.jenshen.smartmirror.ui.mvp.view.signup.tuner.SignUpTunerView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(SignUpTunerModule::class))
interface SignUpTunerComponent : PresenterComponent<SignUpTunerView, SignUpTunerPresenter>, ActivityComponent<SignUpTunerActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<SignUpTunerComponent>
}