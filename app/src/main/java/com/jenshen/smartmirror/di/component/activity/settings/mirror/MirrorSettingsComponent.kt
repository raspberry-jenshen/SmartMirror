package com.jenshen.smartmirror.di.component.activity.settings.mirror

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.settings.mirror.MirrorSettingsModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.settings.mirror.MirrorSettingsActivity
import com.jenshen.smartmirror.ui.mvp.presenter.settings.mirror.MirrorSettingsPresenter
import com.jenshen.smartmirror.ui.mvp.view.settings.mirror.MirrorSettingsView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(MirrorSettingsModule::class))
interface MirrorSettingsComponent : PresenterComponent<MirrorSettingsView, MirrorSettingsPresenter>, ActivityComponent<MirrorSettingsActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<MirrorSettingsComponent>
}