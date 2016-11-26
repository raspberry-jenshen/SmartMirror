package com.jenshen.smartmirror.di.component.activity.signUp.mirror

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.signUp.mirror.SignUpMirrorModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.signup.mirror.SignUpMirrorActivity
import com.jenshen.smartmirror.ui.mvp.presenter.signup.mirror.SignUpMirrorPresenter
import com.jenshen.smartmirror.ui.mvp.view.signup.mirror.SignUpMirrorView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(SignUpMirrorModule::class))
interface SignUpMirrorComponent : PresenterComponent<SignUpMirrorView, SignUpMirrorPresenter>, ActivityComponent<SignUpMirrorActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<SignUpMirrorComponent>
}