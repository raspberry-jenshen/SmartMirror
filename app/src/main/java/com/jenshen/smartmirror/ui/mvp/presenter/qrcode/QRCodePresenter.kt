package com.jenshen.smartmirror.ui.mvp.presenter.qrcode

import android.content.Context
import android.net.wifi.WifiManager
import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.interactor.firebase.auth.FirebaseAuthInteractor
import com.jenshen.smartmirror.manager.db.firebase.FirebaseRealtimeDatabaseManager
import com.jenshen.smartmirror.manager.db.firebase.RealtimeDatabaseManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.manager.session.SessionManager
import com.jenshen.smartmirror.ui.mvp.view.qrcode.QRCodeView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class QRCodePresenter @Inject constructor(private val authInteractor: FirebaseAuthInteractor,
                                          private val realtimeDatabaseManager: RealtimeDatabaseManager) : MvpRxPresenter<QRCodeView>() {

    override fun attachView(view: QRCodeView?) {
        super.attachView(view)
        authInteractor.fetchAuth()
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onLoginSuccess() }, { view?.handleError(it) })
    }

    fun signInMirror() {
        authInteractor.signInMirror()
                .andThen(Single.fromCallable { view?.context }
                        .filter { it != null }
                        .map { getMacAddress(it!!) }
                        .flatMapSingle { realtimeDatabaseManager.createOrGetMirror(it) })
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onUserPreviousLoaded(it) }, { view?.onLoginClicked() })
    }

    private fun getMacAddress(context: Context): String {
        val manager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = manager.connectionInfo
        return info.macAddress
    }
}