package com.jenshen.smartmirror.ui.activity.start.splash

import android.content.Intent
import android.os.Bundle
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.component.activity.start.splash.SplashComponent
import com.jenshen.smartmirror.ui.activity.choose.mirror.ChooseMirrorActivity
import com.jenshen.smartmirror.ui.activity.dashboard.mirror.MirrorDashboardActivity
import com.jenshen.smartmirror.ui.activity.choose.account.ChooseAccountActivity
import com.jenshen.smartmirror.ui.mvp.presenter.start.splash.SplashPresenter
import com.jenshen.smartmirror.ui.mvp.view.start.splash.SplashView

class SplashActivity : BaseDiMvpActivity<SplashComponent, SplashView, SplashPresenter>(), SplashView {


    /* inject */

    override fun createComponent(): SplashComponent {
        return SmartMirrorApp
                .activityComponentBuilders[SplashActivity::class.java]?.build() as SplashComponent
    }

    override fun injectMembers(instance: SplashComponent) {
        instance.injectMembers(this)
    }


    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.isSessionExist()
    }


    /* callbacks */

    override fun openMirrorScreen() {
        startActivity(Intent(context, MirrorDashboardActivity::class.java))
    }

    override fun openMirrorTunerScreen() {
        ChooseMirrorActivity.start(context)
    }

    override fun openStartScreen() {
        startActivity(Intent(context, ChooseAccountActivity::class.java))
    }
}
