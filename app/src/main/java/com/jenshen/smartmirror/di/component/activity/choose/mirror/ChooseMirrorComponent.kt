package com.jenshen.smartmirror.di.component.activity.choose.mirror

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterLceComponent
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.di.module.activity.choose.mirror.ChooseMirrorModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.choose.mirror.ChooseMirrorActivity
import com.jenshen.smartmirror.ui.mvp.presenter.choose.mirror.ChooseMirrorPresenter
import com.jenshen.smartmirror.ui.mvp.view.choose.mirror.ChooseMirrorView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ChooseMirrorModule::class))
interface ChooseMirrorComponent : PresenterLceComponent<MirrorModel, ChooseMirrorView, ChooseMirrorPresenter>,
        ActivityComponent<ChooseMirrorActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<ChooseMirrorComponent>
}