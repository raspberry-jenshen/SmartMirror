package com.jenshen.smartmirror.ui.activity.start

import android.content.Intent
import android.os.Bundle
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.Application
import com.jenshen.smartmirror.di.component.activity.StartComponent
import com.jenshen.smartmirror.ui.activity.mirror.QRCodeActivity
import com.jenshen.smartmirror.ui.mvp.presenter.start.StartPresenter
import com.jenshen.smartmirror.ui.mvp.view.start.StartView
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : BaseDiMvpActivity<StartComponent, StartView, StartPresenter>() {

    /* inject */

    override fun createComponent(): StartComponent = Application.appComponent.plusStartComponent()

    override fun inject(fragmentComponent: StartComponent) {
        fragmentComponent.inject(this)
    }

    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)


        mirror_button.setOnClickListener {
            startActivity(Intent(getContext(), QRCodeActivity::class.java))
        }
        presenter.isSessionExist()
    }
}
