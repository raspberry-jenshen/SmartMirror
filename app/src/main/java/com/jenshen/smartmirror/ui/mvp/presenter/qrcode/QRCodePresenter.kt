package com.jenshen.smartmirror.ui.mvp.presenter.qrcode

import android.content.Context
import android.provider.Settings.Secure
import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.interactor.firebase.api.ApiInteractor
import com.jenshen.smartmirror.interactor.firebase.auth.FirebaseAuthInteractor
import com.jenshen.smartmirror.ui.mvp.view.qrcode.QRCodeView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class QRCodePresenter @Inject constructor(private val authInteractor: FirebaseAuthInteractor,
                                          private val apiInteractor: ApiInteractor) : MvpRxPresenter<QRCodeView>() {

    override fun attachView(view: QRCodeView?) {
        super.attachView(view)
        authInteractor.fetchAuth()
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({
                    if (view != null) {
                        createMirrorAccount(view.context)
                    }
                }, { view?.handleError(it) })
    }

    fun signInMirror() {
        view?.showProgress()
        authInteractor.signInMirror()
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({
                    view?.hideProgress()
                }, {
                    view?.handleError(it)
                    view?.hideProgress()
                })
    }

    private fun createMirrorAccount(context: Context) {
        view?.showProgress()
        apiInteractor.createOrGetMirror(getDeviceUniqueID(context))
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({
                    view?.onMirrorCreated(it)
                    view?.hideProgress()
                }, {
                    view?.handleError(it)
                    view?.hideProgress()
                })
    }

    private fun getDeviceUniqueID(activity: Context) = Secure.getString(activity.contentResolver, Secure.ANDROID_ID)
}