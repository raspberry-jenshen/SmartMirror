package com.jenshen.smartmirror.ui.mvp.view.choose.account

import com.jenshen.compat.base.view.BaseMvpView


interface ChooseAccountView : BaseMvpView {

    fun onChooseMirrorAccount()
    fun onUpdateTimer(seconds: Int)
}