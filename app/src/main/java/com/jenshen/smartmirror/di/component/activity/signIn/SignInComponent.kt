package com.jenshen.smartmirror.di.component.activity.signIn

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.signUp.mirror.SignUpMirrorModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.signIn.SignInTunerActivity
import com.jenshen.smartmirror.ui.mvp.presenter.signin.SignInPresenter
import com.jenshen.smartmirror.ui.mvp.view.signin.SignInView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(SignUpMirrorModule::class))
interface SignInComponent : PresenterComponent<SignInView, SignInPresenter>, ActivityComponent<SignInTunerActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<SignInComponent>
}