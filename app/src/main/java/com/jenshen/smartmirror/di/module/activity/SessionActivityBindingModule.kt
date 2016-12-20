package com.jenshen.smartmirror.di.module.activity

import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.smartmirror.di.component.activity.add.widget.AddWidgetComponent
import com.jenshen.smartmirror.di.component.activity.choose.mirror.ChooseMirrorComponent
import com.jenshen.smartmirror.di.component.activity.choose.widget.ChooseWidgetComponent
import com.jenshen.smartmirror.di.component.activity.dashboard.mirror.MirrorDashboardComponent
import com.jenshen.smartmirror.di.component.activity.edit.mirror.EditMirrorComponent
import com.jenshen.smartmirror.di.component.activity.settings.mirror.MirrorSettingsComponent
import com.jenshen.smartmirror.di.multibuildings.ActivityKey
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.ui.activity.add.widget.AddWidgetActivity
import com.jenshen.smartmirror.ui.activity.choose.mirror.ChooseMirrorActivity
import com.jenshen.smartmirror.ui.activity.choose.widget.ChooseWidgetActivity
import com.jenshen.smartmirror.ui.activity.dashboard.mirror.MirrorDashboardActivity
import com.jenshen.smartmirror.ui.activity.edit.mirror.EditMirrorActivity
import com.jenshen.smartmirror.ui.activity.settings.mirror.MirrorSettingsActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(subcomponents = arrayOf(
        MirrorDashboardComponent::class,
        ChooseMirrorComponent::class,
        EditMirrorComponent::class,
        AddWidgetComponent::class,
        ChooseWidgetComponent::class,
        MirrorSettingsComponent::class))
abstract class SessionActivityBindingModule {

    @SessionScope
    @Binds
    @IntoMap
    @ActivityKey(MirrorDashboardActivity::class)
    abstract fun bindMirrorDashboardActivity(impl: MirrorDashboardComponent.Builder): ActivityComponentBuilder<*>

    @SessionScope
    @Binds
    @IntoMap
    @ActivityKey(ChooseMirrorActivity::class)
    abstract fun bindChooseMirrorActivity(impl: ChooseMirrorComponent.Builder): ActivityComponentBuilder<*>

    @SessionScope
    @Binds
    @IntoMap
    @ActivityKey(EditMirrorActivity::class)
    abstract fun bindEditMirrorActivity(impl: EditMirrorComponent.Builder): ActivityComponentBuilder<*>

    @SessionScope
    @Binds
    @IntoMap
    @ActivityKey(AddWidgetActivity::class)
    abstract fun bindAddWidgetActivity(impl: AddWidgetComponent.Builder): ActivityComponentBuilder<*>

    @SessionScope
    @Binds
    @IntoMap
    @ActivityKey(ChooseWidgetActivity::class)
    abstract fun bindChooseWidgetActivity(impl: ChooseWidgetComponent.Builder): ActivityComponentBuilder<*>

    @SessionScope
    @Binds
    @IntoMap
    @ActivityKey(MirrorSettingsActivity::class)
    abstract fun bindMirrorSettingsActivity(impl: MirrorSettingsComponent.Builder): ActivityComponentBuilder<*>
}