package com.jenshen.smartmirror.ui.mvp.presenter.start.service

import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.manager.firebase.auth.AuthManager
import com.jenshen.smartmirror.ui.mvp.view.start.service.StartMirrorServiceView
import io.reactivex.Single
import javax.inject.Inject

class StartMirrorServisePresenter @Inject constructor(private val authManager: AuthManager) :
        MvpRxPresenter<StartMirrorServiceView>() {

    fun isSessionExist() {
        Single.fromCallable { authManager.isUserExists }
                .subscribe({ isSessionExist ->
                    if (isSessionExist) {
                        view?.openMirrorScreen()
                    }
                }, { view?.handleError(it) })
    }
}