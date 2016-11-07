package com.jenshen.smartmirror.ui.mvp.presenter.start

import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.ui.mvp.view.start.StartView
import ua.regin.pocket.manager.preference.PreferencesManager
import javax.inject.Inject

class StartPresenter : MvpRxPresenter<StartView> {

    private val preferencesManager: PreferencesManager

    @Inject constructor(preferencesManager: PreferencesManager) {
        this.preferencesManager = preferencesManager
    }

    fun isSessionExist() {

    }
}