package com.jenshen.smartmirror.di.module.activity

import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.smartmirror.di.component.activity.dashboard.mirror.MirrorDashboardComponent
import com.jenshen.smartmirror.di.component.activity.dashboard.tuner.TunerDashboardComponent
import com.jenshen.smartmirror.di.multibuildings.ActivityKey
import com.jenshen.smartmirror.ui.activity.dashboard.mirror.MirrorDashboardActivity
import com.jenshen.smartmirror.ui.activity.dashboard.tuner.TunerDashboardActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(subcomponents = arrayOf(
        MirrorDashboardComponent::class,
        TunerDashboardComponent::class))
abstract class SessionActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MirrorDashboardActivity::class)
    abstract fun mirrorActivityComponentBuilder(impl: MirrorDashboardComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(TunerDashboardActivity::class)
    abstract fun tunerActivityComponentBuilder(impl: TunerDashboardComponent.Builder): ActivityComponentBuilder<*>
}