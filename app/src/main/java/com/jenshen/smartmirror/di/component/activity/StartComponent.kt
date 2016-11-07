package com.jenshen.smartmirror.di.component.activity

import com.jenshen.compat.base.component.InjectedView
import com.jenshen.compat.base.component.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.start.StartModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.start.StartActivity
import com.jenshen.smartmirror.ui.mvp.presenter.start.StartPresenter
import com.jenshen.smartmirror.ui.mvp.view.start.StartView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(StartModule::class))
interface StartComponent : PresenterComponent<StartView, StartPresenter>, InjectedView<StartActivity> {

}