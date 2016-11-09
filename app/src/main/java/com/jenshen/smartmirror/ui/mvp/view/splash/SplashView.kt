package com.jenshen.smartmirror.ui.mvp.view.splash

import com.jenshen.compat.base.view.BaseMvpView

interface SplashView : BaseMvpView {
    fun openMirrorScreen()
    fun openMirrorTunerScreen()
    fun openStartScreen()
}