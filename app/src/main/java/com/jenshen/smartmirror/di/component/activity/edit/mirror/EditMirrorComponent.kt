package com.jenshen.smartmirror.di.component.activity.edit.mirror

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.edit.mirror.EditMirrorModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.ui.activity.edit.mirror.EditMirrorActivity
import com.jenshen.smartmirror.ui.mvp.presenter.edit.mirror.EditMirrorPresenter
import com.jenshen.smartmirror.ui.mvp.view.edit.mirror.EditMirrorView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(EditMirrorModule::class))
interface EditMirrorComponent : PresenterComponent<EditMirrorView, EditMirrorPresenter>,
        ActivityComponent<EditMirrorActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<EditMirrorComponent>
}