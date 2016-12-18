package com.jenshen.smartmirror.ui.mvp.presenter.edit.mirror


import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.entity.widget.updater.WidgetUpdater
import com.jenshen.smartmirror.data.model.mirror.EditMirrorModel
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.manager.widget.factory.WidgetFactoryManager
import com.jenshen.smartmirror.ui.mvp.view.edit.mirror.EditMirrorView
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshen.smartmirror.util.reactive.applyProgress
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditMirrorPresenter @Inject constructor(private val tunerApiInteractor: TunerApiInteractor,
                                              private val widgetFactoryManager: WidgetFactoryManager) : MvpRxPresenter<EditMirrorView>() {

    private val updaterList: MutableList<WidgetUpdater<*>>

    init {
        updaterList = mutableListOf()
    }

    fun saveConfiguration(editMirrorModel: EditMirrorModel) {
        tunerApiInteractor.saveMirrorConfiguration(editMirrorModel)
                .applySchedulers(Schedulers.io())
                .applyProgress(Consumer { view?.showProgress() }, Action { view?.hideProgress() })
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onSavedConfiguration() }, { view?.handleError(it) })
    }

    fun loadMirrorConfiguration(configurationKey: String) {
        tunerApiInteractor.getMirrorConfiguration(configurationKey)
                .applySchedulers(Schedulers.io())
                .applyProgress(Consumer { view?.showProgress() }, Action { view?.hideProgress() })
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onMirrorConfigurationLoaded(it) }, { view?.handleError(it) })
    }

    fun addWidgetUpdater(widgetKey: WidgetKey) {
        val updater = widgetFactoryManager.getUpdaterForWidget(widgetKey)
        updaterList.add(updater)
        updater.startUpdate()
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onWidgetUpdate(it) }, { view?.handleError(it) })
    }

    fun updateWidget(infoData: WidgetData, widget: Widget<*>) {
        widgetFactoryManager.updateWidget(infoData, widget)
    }

    fun clearWidgetsUpdaters() {
        updaterList.forEach { it.apply { clear() } }
        updaterList.clear()
    }
}
