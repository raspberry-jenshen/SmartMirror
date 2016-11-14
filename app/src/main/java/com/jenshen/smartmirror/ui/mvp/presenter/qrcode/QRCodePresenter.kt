package com.jenshen.smartmirror.ui.mvp.presenter.qrcode

import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.interactor.firebase.auth.FirebaseAuthInteractor
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.manager.session.SessionManager
import com.jenshen.smartmirror.ui.mvp.view.qrcode.QRCodeView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class QRCodePresenter @Inject constructor(private val authInteractor: FirebaseAuthInteractor) : MvpRxPresenter<QRCodeView>() {

    override fun attachView(view: QRCodeView?) {
        super.attachView(view)
        authInteractor.fetchAuth()
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onLoginSuccess() }, { view?.handleError(it) })
    }

    fun signInMirror() {
        /*authInteractor.signInMirror()
                .andThen(sessionManager.getUser().isEmpty)
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onUserPreviousLoaded(it) }, { view?.onLoginClicked() })*/
    }
}