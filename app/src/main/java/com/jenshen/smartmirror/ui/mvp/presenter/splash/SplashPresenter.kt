package com.jenshen.smartmirror.ui.mvp.presenter.splash

import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.ui.mvp.view.splash.SplashView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter @Inject constructor(private val preferencesManager: PreferencesManager) : MvpRxPresenter<SplashView>() {

    fun isSessionExist() {
        Single.zip(preferencesManager.isSessionExist(),
                Single.fromCallable { preferencesManager.isMirror() },
                BiFunction(::SessionInfo))
                .delay(1, TimeUnit.SECONDS)
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

    class SessionInfo(val isSessionExist: Boolean, val isMirror: Boolean)
}