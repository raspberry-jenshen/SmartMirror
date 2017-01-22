package com.jenshen.smartmirror.ui.mvp.presenter.choose.account


import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.ui.mvp.view.choose.account.ChooseAccountView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChooseAccountPresenter @Inject constructor() : MvpRxPresenter<ChooseAccountView>() {

    private var disposable: Disposable? = null

    fun startTimeout() {
        var seconds = 10
        Completable.timer(seconds.toLong(), TimeUnit.SECONDS)
                .applySchedulers(Schedulers.computation())
                .doOnSubscribe {
                    disposable = it
                    addDisposible(it)
                }
                .subscribe({ view?.onChooseMirrorAccount() }, { view?.handleError(it) })

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .applySchedulers(Schedulers.computation())
                .takeWhile { seconds > -1 }
                .doOnSubscribe { addDisposible(it) }
                .subscribe({
                    view?.onUpdateTimer(seconds)
                    seconds--
                }, { view?.handleError(it) })
    }

    fun cancelTimer() {
        if (disposable != null && !disposable!!.isDisposed) {
            view?.onUpdateTimer(0)
            compositeDisposable.remove(disposable)
        }
    }
}
