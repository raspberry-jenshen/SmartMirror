package com.jenshen.smartmirror.ui.mvp.presenter.choose.mirror


import com.jenshen.compat.base.presenter.MvpLceRxPresenter
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.ui.mvp.view.choose.mirror.ChooseMirrorView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChooseMirrorPresenter @Inject constructor(private val apiInteractor: TunerApiInteractor) : MvpLceRxPresenter<MirrorModel, ChooseMirrorView>() {

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

    fun fetchMirrors(pullToRefresh: Boolean) {
        subscribeOnModel(apiInteractor.fetchMirrorsForTuner().toObservable(), pullToRefresh)
    }
}
