package com.jenshen.smartmirror.di.component.activity.dashboard.tuner

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.dashboard.tuner.TunerDashboardModule
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.ui.activity.dashboard.tuner.TunerActivity
import com.jenshen.smartmirror.ui.mvp.presenter.dashboard.tuner.TunerDashboardPresenter
import com.jenshen.smartmirror.ui.mvp.view.dashboard.tuner.TunerDashboardView
import dagger.Subcomponent

@SessionScope
@Subcomponent(modules = arrayOf(TunerDashboardModule::class))
interface TunerDashboardComponent : PresenterComponent<TunerDashboardView, TunerDashboardPresenter>,
        ActivityComponent<TunerActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<TunerDashboardComponent>
}