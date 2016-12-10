package com.jenshen.smartmirror.ui.activity.dashboard.mirror

import android.content.Intent
import android.os.Bundle
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.di.component.activity.dashboard.mirror.MirrorDashboardComponent
import com.jenshen.smartmirror.ui.activity.signup.mirror.SignUpMirrorActivity
import com.jenshen.smartmirror.ui.mvp.presenter.dashboard.mirror.MirrorDashboardPresenter
import com.jenshen.smartmirror.ui.mvp.view.dashboard.mirror.MirrorDashboardView
import com.jenshen.smartmirror.util.widget.createWidget
import kotlinx.android.synthetic.main.activity_dashboard_mirror.*


class MirrorDashboardActivity : BaseDiMvpActivity<MirrorDashboardComponent, MirrorDashboardView, MirrorDashboardPresenter>(), MirrorDashboardView {

    /* inject */

    override fun createComponent(): MirrorDashboardComponent {
        return SmartMirrorApp
                .sessionActivityComponentBuilders!![MirrorDashboardActivity::class.java]!!
                .build() as MirrorDashboardComponent
    }

    override fun injectMembers(instance: MirrorDashboardComponent) {
        instance.injectMembers(this)
    }

    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_mirror)
        //widgetContainer.isEnabled = false
    }

    /* callbacks */

    override fun showSignUpScreen() {
        val intent = Intent(context, SignUpMirrorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun updateMirrorConfiguration(mirrorConfiguration: MirrorConfiguration) {
        mirrorConfiguration.widgets?.forEach {
            val widget = createWidget(it.value.widgetKey, context)
            val position = widget.widgetPosition

            position.topLeftColumnLine = it.value.topLeftCorner.column
            position.topLeftRowLine = it.value.topLeftCorner.row

            position.topRightColumnLine = it.value.topRightCorner.column
            position.topRightRowLine = it.value.topRightCorner.row

            position.bottomLeftColumnLine = it.value.bottomLeftCorner.column
            position.bottomLeftRowLine = it.value.bottomLeftCorner.row

            position.bottomRightColumnLine = it.value.bottomRightCorner.column
            position.bottomRightRowLine = it.value.bottomRightCorner.row

            widgetContainer.addWidgetView(widget)
        }
    }
}