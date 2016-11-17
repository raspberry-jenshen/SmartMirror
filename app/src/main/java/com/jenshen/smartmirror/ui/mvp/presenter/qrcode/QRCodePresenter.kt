package com.jenshen.smartmirror.ui.mvp.presenter.qrcode

import android.content.Context
import android.net.wifi.WifiManager
import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.interactor.firebase.api.ApiInteractor
import com.jenshen.smartmirror.interactor.firebase.auth.FirebaseAuthInteractor
import com.jenshen.smartmirror.ui.mvp.view.qrcode.QRCodeView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class QRCodePresenter @Inject constructor(private val authInteractor: FirebaseAuthInteractor,
                                          private val apiInteractor: ApiInteractor) : MvpRxPresenter<QRCodeView>() {

    override fun attachView(view: QRCodeView?) {
        super.attachView(view)
        authInteractor.fetchAuth()
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onLoginSuccess() }, { view?.handleError(it) })
    }

    fun signInMirror() {
        authInteractor.signInMirror()
                .andThen(apiInteractor.createOrGetMirror(getUUID()))
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onMirrorCreated(it) }, { view?.handleError(it) })
    }

    private fun getMacAddress(context: Context): String {
        val manager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = manager.connectionInfo
        return info.macAddress
    }

    private fun getUUID(): String {
        return UUID.randomUUID().toString()
    }
}