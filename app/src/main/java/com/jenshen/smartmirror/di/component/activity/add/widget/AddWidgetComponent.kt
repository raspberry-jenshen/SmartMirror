package com.jenshen.smartmirror.di.component.activity.add.widget

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.add.widget.AddWidgetModule
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.ui.activity.add.widget.AddWidgetActivity
import com.jenshen.smartmirror.ui.mvp.presenter.add.widget.AddWidgetPresenter
import com.jenshen.smartmirror.ui.mvp.view.add.widget.AddWidgetView
import dagger.Subcomponent

@SessionScope
@Subcomponent(modules = arrayOf(AddWidgetModule::class))
interface AddWidgetComponent : PresenterComponent<AddWidgetView, AddWidgetPresenter>,
        ActivityComponent<AddWidgetActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<AddWidgetComponent>
}