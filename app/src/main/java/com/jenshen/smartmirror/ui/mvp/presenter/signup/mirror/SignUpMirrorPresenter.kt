package com.jenshen.smartmirror.ui.mvp.presenter.signup.mirror

import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.interactor.firebase.api.ApiInteractor
import com.jenshen.smartmirror.interactor.firebase.auth.FirebaseAuthInteractor
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.ui.mvp.view.signup.mirror.SignUpMirrorView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SignUpMirrorPresenter @Inject constructor(private val authInteractor: FirebaseAuthInteractor,
                                                private val apiInteractor: ApiInteractor,
                                                private val preferencesManager: PreferencesManager) : MvpRxPresenter<SignUpMirrorView>() {

    private var isTaskFinished = true

    override fun attachView(view: SignUpMirrorView?) {
        super.attachView(view)
        authInteractor.fetchAuth()
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ createMirrorAccount() }, { view?.handleError(it) })
    }

    fun signInMirror() {
        view?.showProgress()
        isTaskFinished = false
        authInteractor.signInMirror()
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({
                    if (isTaskFinished) {
                        view?.hideProgress()
                    }
                }, {
                    view?.handleError(it)
                    view?.hideProgress()
                    isTaskFinished = true
                })
    }

    private fun createMirrorAccount() {
        if (isTaskFinished) {
            view?.showProgress()
            isTaskFinished = false
        }
        Single.fromCallable { preferencesManager.getSession()!! }
                .cast(MirrorSession::class.java)
                .flatMap { apiInteractor.createOrGetMirror(it) }
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({
                    view?.onMirrorCreated(it)
                    view?.hideProgress()
                    isTaskFinished = true
                }, {
                    view?.handleError(it)
                    view?.hideProgress()
                    isTaskFinished = true
                })
    }
}