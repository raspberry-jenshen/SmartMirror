package com.jenshen.smartmirror.ui.mvp.presenter.dashboard.tuner


import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.ui.mvp.view.dashboard.tuner.TunerDashboardView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TunerDashboardPresenter @Inject constructor(private val apiInteractor: TunerApiInteractor) : MvpRxPresenter<TunerDashboardView>() {

    fun subscribeOnMirror(mirrorId: String) {
        view?.showProgress()
        apiInteractor.subscribeOnMirror(mirrorId)
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.hideProgress() }, {
                    view?.handleError(it)
                    view?.hideProgress()
                })
    }

}
