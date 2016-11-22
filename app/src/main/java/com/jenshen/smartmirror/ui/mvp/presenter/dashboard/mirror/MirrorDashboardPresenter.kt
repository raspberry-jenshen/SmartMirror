package com.jenshen.smartmirror.ui.mvp.presenter.dashboard.mirror

import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.interactor.firebase.api.mirror.MirrorApiInteractor
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.ui.mvp.view.dashboard.mirror.MirrorDashboardView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MirrorDashboardPresenter @Inject constructor(private val mirrorApiInteractor: MirrorApiInteractor,
                                                   private  val preferencesManager: PreferencesManager) : MvpRxPresenter<MirrorDashboardView>() {

    override fun attachView(view: MirrorDashboardView?) {
        super.attachView(view)
        fetchIsNeedToShoQrCode()
    }

    private fun fetchIsNeedToShoQrCode() {
       addDisposible(mirrorApiInteractor.fetchIsNeedToShoQrCode(preferencesManager.getSession()!!.id)
                .applySchedulers(Schedulers.computation())
                .subscribe({ view?.showSignUpScreen() }, { view?.handleError(it) }))
    }
}