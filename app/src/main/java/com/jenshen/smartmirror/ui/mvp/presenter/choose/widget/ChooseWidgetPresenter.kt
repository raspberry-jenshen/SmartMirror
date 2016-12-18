package com.jenshen.smartmirror.ui.mvp.presenter.choose.widget


import com.jenshen.compat.base.presenter.MvpLceRxPresenter
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.entity.widget.updater.WidgetUpdater
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.data.model.widget.WidgetModel
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.manager.widget.factory.WidgetFactoryManager
import com.jenshen.smartmirror.ui.mvp.view.choose.widget.ChooseWidgetView
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChooseWidgetPresenter @Inject constructor(private val apiInteractor: TunerApiInteractor,
                                                private val widgetFactoryManager: WidgetFactoryManager) : MvpLceRxPresenter<WidgetModel, ChooseWidgetView>() {

    private val updaterList: MutableList<WidgetUpdater<*>>

    init {
        updaterList = mutableListOf()
    }

    fun fetchWidgets(pullToRefresh: Boolean) {
        subscribeOnModel(apiInteractor
                .fetchWidgets()
                .toObservable(), pullToRefresh)
        Completable.timer(1000, TimeUnit.MILLISECONDS)
                .applySchedulers(Schedulers.computation())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.showContent() }, { view?.handleError(it) })
    }

    fun loadInfoForWidget(widgetKey: String) {
        val updaterForWidget = widgetFactoryManager.getUpdaterForWidget(WidgetKey(widgetKey))
        updaterList.add(updaterForWidget)
        updaterForWidget.startUpdate()
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onWidgetUpdate(it) }, { view?.handleError(it) })
    }

    fun clearWidgetsUpdaters() {
        updaterList.forEach { it.apply { clear() } }
        updaterList.clear()
    }

    fun updateWidget(infoData: WidgetData, widget: Widget<*>) {
        widgetFactoryManager.updateWidget(infoData, widget)
    }
}
