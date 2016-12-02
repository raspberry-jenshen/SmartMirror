package com.jenshen.smartmirror.ui.mvp.presenter.add.widget


import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.ui.mvp.view.add.widget.AddWidgetView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddWidgetPresenter @Inject constructor(private val tunerApiInteractor: TunerApiInteractor) : MvpRxPresenter<AddWidgetView>() {

    fun createWidget(name: String, width: String, height: String) {
        view?.showProgress()
        val widthSize = width.toInt()
        val heightSize = height.toInt()
        tunerApiInteractor.addWidget(name, widthSize, heightSize)
                .applySchedulers(Schedulers.computation())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.hideProgress() }, {
                    view?.handleError(it)
                    view?.hideProgress()
                })

    }
}
