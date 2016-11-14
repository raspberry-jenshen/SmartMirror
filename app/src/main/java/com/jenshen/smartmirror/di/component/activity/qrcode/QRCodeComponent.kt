package com.jenshen.smartmirror.di.component.activity.qrcode

import com.jenshen.compat.base.component.activity.ActivityComponent
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.presenter.PresenterComponent
import com.jenshen.smartmirror.di.module.activity.qrcode.QRCodeModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.ui.activity.qrcode.QRCodeActivity
import com.jenshen.smartmirror.ui.activity.SignInActivity
import com.jenshen.smartmirror.ui.mvp.presenter.qrcode.QRCodePresenter
import com.jenshen.smartmirror.ui.mvp.presenter.signin.SignInPresenter
import com.jenshen.smartmirror.ui.mvp.view.qrcode.QRCodeView
import com.jenshen.smartmirror.ui.mvp.view.signIn.SignInView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(QRCodeModule::class))
interface QRCodeComponent : PresenterComponent<QRCodeView, QRCodePresenter>, ActivityComponent<QRCodeActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<QRCodeComponent>
}