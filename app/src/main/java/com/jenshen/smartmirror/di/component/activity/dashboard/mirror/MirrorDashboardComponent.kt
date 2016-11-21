package com.jenshen.smartmirror.di.component.activity.dashboard.mirror

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.dashboard.mirror.MirrorDashboardModule
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.ui.activity.dashboard.mirror.MirrorDashboardActivity
import com.jenshen.smartmirror.ui.mvp.presenter.dashboard.mirror.MirrorDashboardPresenter
import com.jenshen.smartmirror.ui.mvp.view.dashboard.mirror.MirrorDashboardView
import dagger.Subcomponent

@SessionScope
@Subcomponent(modules = arrayOf(MirrorDashboardModule::class))
interface MirrorDashboardComponent : PresenterComponent<MirrorDashboardView, MirrorDashboardPresenter>,
        ActivityComponent<MirrorDashboardActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<MirrorDashboardComponent>
}