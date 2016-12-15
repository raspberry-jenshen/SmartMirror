package com.jenshen.smartmirror.ui.mvp.view.dashboard.mirror

import com.jenshen.compat.base.view.BaseMvpView
import com.jenshen.smartmirror.data.entity.widget.info.InfoForWidget
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration


interface MirrorDashboardView : BaseMvpView {
    fun showSignUpScreen()
    fun updateMirrorConfiguration(mirrorConfiguration: MirrorConfiguration)
    fun onWidgetUpdate(info: InfoForWidget)
}