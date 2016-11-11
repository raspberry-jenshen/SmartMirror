package com.jenshen.smartmirror.di.component.activity.signUp

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.signUp.SignUpModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.signUp.SignUpActivity
import com.jenshen.smartmirror.ui.mvp.presenter.signUp.SignUpPresenter
import com.jenshen.smartmirror.ui.mvp.view.signUp.SignUpView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(SignUpModule::class))
interface SignUpComponent : PresenterComponent<SignUpView, SignUpPresenter>, ActivityComponent<SignUpActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<SignUpComponent>
}