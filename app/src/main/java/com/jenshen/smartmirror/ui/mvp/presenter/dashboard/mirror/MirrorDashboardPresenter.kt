package com.jenshen.smartmirror.ui.mvp.presenter.dashboard.mirror

import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.entity.widget.updater.WidgetUpdater
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.interactor.firebase.api.mirror.MirrorApiInteractor
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.manager.widget.factory.WidgetFactoryManager
import com.jenshen.smartmirror.ui.mvp.view.dashboard.mirror.MirrorDashboardView
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MirrorDashboardPresenter @Inject constructor(private val mirrorApiInteractor: MirrorApiInteractor,
                                                   private val preferencesManager: PreferencesManager,
                                                   private val widgetFactoryManager: WidgetFactoryManager) :
        MvpRxPresenter<MirrorDashboardView>(), MirrorApiInteractor by mirrorApiInteractor {
    private val updaterList: MutableList<WidgetUpdater<*>>

    init {
        updaterList = mutableListOf()
    }

    override fun attachView(view: MirrorDashboardView?) {
        super.attachView(view)
        fetchIsNeedToShowQrCode()
        fetchSelectedConfiguration()
    }

    fun addWidgetUpdater(widgetKey: WidgetKey) {
        val updater = widgetFactoryManager.getUpdaterForWidget(widgetKey)
        updaterList.add(updater)
        updater.startUpdate()
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

    /* private methods */

    private fun fetchIsNeedToShowQrCode() {
        addDisposible(mirrorApiInteractor.fetchIsNeedToShowQrCode(preferencesManager.getSession()!!.key)
                .applySchedulers(Schedulers.computation())
                .subscribe({ view?.showSignUpScreen() }, { view?.handleError(it) }))
    }

    private fun fetchSelectedConfiguration() {
        addDisposible(mirrorApiInteractor.fetchSelectedMirrorConfiguration(preferencesManager.getSession()!!.key)
                .applySchedulers(Schedulers.computation())
                .subscribe({ view?.updateMirrorConfiguration(it) }, { view?.handleError(it) }))
    }
}