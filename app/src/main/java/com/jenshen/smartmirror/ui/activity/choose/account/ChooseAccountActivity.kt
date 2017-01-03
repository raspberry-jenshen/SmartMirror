package com.jenshen.smartmirror.ui.activity.choose.account

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.component.activity.choose.account.ChooseAccountComponent
import com.jenshen.smartmirror.ui.activity.signIn.SignInTunerActivity
import com.jenshen.smartmirror.ui.activity.signup.mirror.SignUpMirrorActivity
import com.jenshen.smartmirror.ui.mvp.presenter.choose.account.ChooseAccountPresenter
import com.jenshen.smartmirror.ui.mvp.view.choose.account.ChooseAccountView
import kotlinx.android.synthetic.main.activity_start.*

class ChooseAccountActivity : BaseDiMvpActivity<ChooseAccountComponent, ChooseAccountView, ChooseAccountPresenter>(), ChooseAccountView {

    /* inject */

    override fun createComponent(): ChooseAccountComponent {
        return SmartMirrorApp
                .activityComponentBuilders[ChooseAccountActivity::class.java]?.build() as ChooseAccountComponent
    }

    override fun injectMembers(instance: ChooseAccountComponent) {
        instance.injectMembers(this)
    }

    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        presenter.startTimeout()

        mirror_button.setOnClickListener { onChooseMirrorAccount() }

        mirrorTuner_button.setOnClickListener {
            startActivity(Intent(this, SignInTunerActivity::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.cancelTimer()
    }

    /* callbacks */

    override fun onChooseMirrorAccount() {
        startActivity(Intent(this, SignUpMirrorActivity::class.java))
        finish()
    }

    override fun onUpdateTimer(seconds: Int) {
        timer.text = seconds.toString()
        if (seconds <= 0) {
            timer.visibility = GONE
        }
    }
}