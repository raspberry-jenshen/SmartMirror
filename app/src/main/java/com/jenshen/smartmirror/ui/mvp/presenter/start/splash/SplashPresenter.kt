package com.jenshen.smartmirror.ui.mvp.presenter.start.splash

import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.manager.firebase.auth.AuthManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.ui.mvp.view.start.splash.SplashView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Single
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter @Inject constructor(private val preferencesManager: PreferencesManager,
                                          private val authManager: AuthManager) :
        MvpRxPresenter<SplashView>() {

    fun isSessionExist() {
        Single.zip(
                Single.timer(500, TimeUnit.MILLISECONDS),
                Single.fromCallable { authManager.isUserExists },
                Single.fromCallable { preferencesManager.isMirror() },
                Function3 { time: Long, isSessionExist: Boolean, isMirror: Boolean ->
                    return@Function3 object {
                        val isSessionExist: Boolean = isSessionExist
                        val isMirror: Boolean = isMirror
                    }
                })
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({
                    if (it.isSessionExist) {
                        if (it.isMirror) {
                            view?.openMirrorScreen()
                        } else {
                            view?.openMirrorTunerScreen()
                        }
                    } else {
                        view?.openStartScreen()
                    }
                }, { view?.handleError(it) })
    }
}