package com.jenshen.smartmirror.di.module.activity

import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.smartmirror.di.component.activity.add.widget.AddWidgetComponent
import com.jenshen.smartmirror.di.component.activity.choose.mirror.ChooseMirrorComponent
import com.jenshen.smartmirror.di.component.activity.choose.widget.ChooseWidgetComponent
import com.jenshen.smartmirror.di.component.activity.dashboard.mirror.MirrorDashboardComponent
import com.jenshen.smartmirror.di.component.activity.edit.mirror.EditMirrorComponent
import com.jenshen.smartmirror.di.multibuildings.ActivityKey
import com.jenshen.smartmirror.ui.activity.add.widget.AddWidgetActivity
import com.jenshen.smartmirror.ui.activity.choose.mirror.ChooseMirrorActivity
import com.jenshen.smartmirror.ui.activity.choose.widget.ChooseWidgetActivity
import com.jenshen.smartmirror.ui.activity.dashboard.mirror.MirrorDashboardActivity
import com.jenshen.smartmirror.ui.activity.edit.mirror.EditMirrorActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(subcomponents = arrayOf(
        MirrorDashboardComponent::class,
        ChooseMirrorComponent::class,
        EditMirrorComponent::class,
        AddWidgetComponent::class,
        ChooseWidgetComponent::class))
abstract class SessionActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MirrorDashboardActivity::class)
    abstract fun mirrorActivityComponentBuilder(impl: MirrorDashboardComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(ChooseMirrorActivity::class)
    abstract fun chooseMirrorActivityComponentBuilder(impl: ChooseMirrorComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(EditMirrorActivity::class)
    abstract fun editMirrorActivityComponentBuilder(impl: EditMirrorComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(AddWidgetActivity::class)
    abstract fun addWidgetActivityComponentBuilder(impl: AddWidgetComponent.Builder): ActivityComponentBuilder<*>

    @Binds
    @IntoMap
    @ActivityKey(ChooseWidgetActivity::class)
    abstract fun chooseWidgetActivityComponentBuilder(impl: ChooseWidgetComponent.Builder): ActivityComponentBuilder<*>
}