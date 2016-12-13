package com.jenshen.smartmirror.ui.mvp.presenter.start.service

import android.util.Log
import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.manager.firebase.auth.AuthManager
import com.jenshen.smartmirror.ui.mvp.view.start.service.StartMirrorServiceView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class StartMirrorServicePresenter @Inject constructor(private val authManager: AuthManager) :
        MvpRxPresenter<StartMirrorServiceView>() {

    fun isSessionExist() {
        Single.fromCallable { authManager.isUserExists }
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ isSessionExist ->
                    if (isSessionExist) {
                        view?.openMirrorScreen()
                    }
                }, { view?.handleError(it) })
    }
}