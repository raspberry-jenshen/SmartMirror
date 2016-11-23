package com.jenshen.smartmirror.di.module.activity

import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.smartmirror.di.component.activity.dashboard.mirror.MirrorDashboardComponent
import com.jenshen.smartmirror.di.component.activity.choose.mirror.ChooseMirrorComponent
import com.jenshen.smartmirror.di.component.activity.edit.mirror.EditMirrorComponent
import com.jenshen.smartmirror.di.multibuildings.ActivityKey
import com.jenshen.smartmirror.ui.activity.dashboard.mirror.MirrorDashboardActivity
import com.jenshen.smartmirror.ui.activity.choose.mirror.ChooseMirrorActivity
import com.jenshen.smartmirror.ui.activity.edit.mirror.EditMirrorActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(subcomponents = arrayOf(
        MirrorDashboardComponent::class,
        ChooseMirrorComponent::class,
        EditMirrorComponent::class))
abstract class SessionActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MirrorDashboardActivity::class)
    abstract fun mirrorActivityComponentBuilder(impl: MirrorDashboardComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(ChooseMirrorActivity::class)
    abstract fun tunerActivityComponentBuilder(impl: ChooseMirrorComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(EditMirrorActivity::class)
    abstract fun editMirrorActivityComponentBuilder(impl: ChooseMirrorComponent.Builder): ActivityComponentBuilder<*>
}