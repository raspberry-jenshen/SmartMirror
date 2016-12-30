package com.jenshen.smartmirror.di.component.activity.choose.account

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.component.activity.choose.mirror.ChooseMirrorComponent
import com.jenshen.smartmirror.di.module.activity.choose.account.ChooseAccountModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.choose.account.ChooseAccountActivity
import com.jenshen.smartmirror.ui.activity.choose.mirror.ChooseMirrorActivity
import com.jenshen.smartmirror.ui.mvp.presenter.choose.account.ChooseAccountPresenter
import com.jenshen.smartmirror.ui.mvp.presenter.choose.mirror.ChooseMirrorPresenter
import com.jenshen.smartmirror.ui.mvp.view.choose.account.ChooseAccountView
import com.jenshen.smartmirror.ui.mvp.view.choose.mirror.ChooseMirrorView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ChooseAccountModule::class))
interface ChooseAccountComponent : PresenterComponent<ChooseAccountView, ChooseAccountPresenter>,
        ActivityComponent<ChooseAccountActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<ChooseAccountComponent>
}