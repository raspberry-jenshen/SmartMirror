package com.jenshen.smartmirror.di.component.activity.login

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.login.LoginModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.login.LoginActivity
import com.jenshen.smartmirror.ui.mvp.presenter.login.LoginPresenter
import com.jenshen.smartmirror.ui.mvp.view.login.LoginView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(LoginModule::class))
interface LoginComponent : PresenterComponent<LoginView, LoginPresenter>, ActivityComponent<LoginActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<LoginComponent>
}