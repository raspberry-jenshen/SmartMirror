package com.jenshen.smartmirror.ui.mvp.presenter.choose.widget


import com.jenshen.compat.base.presenter.MvpLceRxPresenter
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import com.jenshen.smartmirror.data.model.widget.WidgetConfigurationModel
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.data.model.widget.WidgetModel
import com.jenshen.smartmirror.data.updater.WidgetUpdater
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.manager.widget.factory.IWidgetFactoryManager
import com.jenshen.smartmirror.ui.mvp.view.choose.widget.ChooseWidgetView
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChooseWidgetPresenter @Inject constructor(private val apiInteractor: TunerApiInteractor,
                                                private val preferencesManager: PreferencesManager,
                                                private val widgetFactoryManager: IWidgetFactoryManager) : MvpLceRxPresenter<WidgetModel, ChooseWidgetView>() {

    private val updaterList: MutableList<WidgetUpdater<*>>

    init {
        updaterList = mutableListOf()
    }

    fun onChooseWidget(widgetModel: WidgetModel) {
        val widgetKey = widgetModel.widgetData.widgetKey

        Single.fromCallable { widgetKey.key == FirebaseRealTimeDatabaseConstant.Widget.CALENDAR_EVENTS_WIDGET_KEY }
                .map { isNeedToAddTunerKey ->
                    val tunerKey: String?
                    if (isNeedToAddTunerKey) {
                        val session = preferencesManager.getSession()!! as TunerSession
                        tunerKey = session.key
                    } else {
                        tunerKey = null
                    }
                    WidgetConfigurationModel(widgetKey, widgetModel.widgetInfo, tunerKey, widgetModel.phrase)
                }
                .applySchedulers(Schedulers.computation())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onReturnWidgetConfiguration(it) }, { view?.handleError(it) })
    }

    fun fetchWidgets(pullToRefresh: Boolean) {
        subscribeOnModel(apiInteractor
                .fetchWidgets()
                .filter { widgetFactoryManager.canSupportThisWidget(it.key) }
                .flatMap { widgetInfo ->
                    widgetFactoryManager.getUpdaterForWidget(WidgetKey(widgetInfo.key))
                            .getInfo()
                            .map { WidgetModel(widgetInfo.data, it) }
                }
                .toObservable(), pullToRefresh)

        Completable.timer(1000, TimeUnit.MILLISECONDS)
                .applySchedulers(Schedulers.computation())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.showContent() }, { view?.handleError(it) })
    }

    fun loadInfoForWidget(widgetKey: String) {
        val updaterForWidget = widgetFactoryManager.getUpdaterForWidget(WidgetKey(widgetKey))
        updaterList.add(updaterForWidget)
        addDisposible(updaterForWidget.startUpdate()
                .applySchedulers(Schedulers.io())
                .subscribe({ view?.onWidgetUpdate(it) }, { view?.handleError(it) }))
    }

    fun clearWidgetsUpdaters() {
        updaterList.forEach { it.apply { clear() } }
        updaterList.clear()
    }

    fun updateWidget(infoData: WidgetData, widget: Widget<*>) {
        widgetFactoryManager.updateWidget(infoData, widget)
    }
}
