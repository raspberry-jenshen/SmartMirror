package com.jenshen.smartmirror.di.component.activity.signIn

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.signIn.SignInModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.signIn.SignInActivity
import com.jenshen.smartmirror.ui.mvp.presenter.signin.SignInPresenter
import com.jenshen.smartmirror.ui.mvp.view.signIn.SignInView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(SignInModule::class))
interface SignInComponent : PresenterComponent<SignInView, SignInPresenter>, ActivityComponent<SignInActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<SignInComponent>
}