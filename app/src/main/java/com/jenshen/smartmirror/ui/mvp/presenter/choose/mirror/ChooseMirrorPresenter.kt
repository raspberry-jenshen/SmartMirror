package com.jenshen.smartmirror.ui.mvp.presenter.choose.mirror


import com.jenshen.compat.base.presenter.MvpLceRxPresenter
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.ui.mvp.view.choose.mirror.ChooseMirrorView
import com.jenshen.smartmirror.util.reactive.applyProgress
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Completable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChooseMirrorPresenter @Inject constructor(private val apiInteractor: TunerApiInteractor) : MvpLceRxPresenter<MirrorModel, ChooseMirrorView>() {

    fun subscribeOnMirror(mirrorId: String) {
        apiInteractor.subscribeOnMirror(mirrorId)
                .applySchedulers(Schedulers.io())
                .applyProgress(Consumer { view?.showProgress() }, Action { view?.hideProgress() })
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({}, { view?.handleError(it) })
    }

    fun fetchMirrors(pullToRefresh: Boolean) {
        subscribeOnModel(apiInteractor.fetchTunerSubscriptions().toObservable(), pullToRefresh)
        Completable.timer(1000, TimeUnit.MILLISECONDS)
                .applySchedulers(Schedulers.computation())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.showContent() }, { view?.handleError(it) })
    }

    fun setMirrorIsWaitingForSubscriber(mirrorId: String) {
        apiInteractor.switchFlagForWaitingTuner(mirrorId)
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({}, { view?.handleError(it) })
    }

    fun deleteSubscription(mirrorKey: String) {
        apiInteractor.unsubscribeFromMirror(mirrorKey)
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({}, { view?.handleError(it) })
    }

    fun setSelectedConfigurationKeyForMirror(configurationId: String?, mirrorId: String) {
        apiInteractor.setSelectedConfigurationKeyForMirror(configurationId, mirrorId)
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({}, { view?.handleError(it) })
    }

    fun deleteConfigurationForMirror(configurationId: String, mirrorId: String) {
        apiInteractor.deleteConfigurationForMirror(configurationId, mirrorId, false)
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({}, { view?.handleError(it) })
    }
}
