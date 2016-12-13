package com.jenshen.smartmirror.ui.mvp.presenter.choose.widget


import com.jenshen.compat.base.presenter.MvpLceRxPresenter
import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.widget.Widget
import com.jenshen.smartmirror.data.model.WidgetConfigurationModel
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.ui.mvp.view.choose.widget.ChooseWidgetView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChooseWidgetPresenter @Inject constructor(private val apiInteractor: TunerApiInteractor) : MvpLceRxPresenter<DataSnapshotWithKey<Widget>, ChooseWidgetView>() {

    fun fetchWidgets(pullToRefresh: Boolean) {
        subscribeOnModel(apiInteractor
                .fetchWidgets()
                .toObservable(), pullToRefresh)
        Completable.timer(1000, TimeUnit.MILLISECONDS)
                .applySchedulers(Schedulers.computation())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.showContent() }, { view?.handleError(it) })
    }
}
