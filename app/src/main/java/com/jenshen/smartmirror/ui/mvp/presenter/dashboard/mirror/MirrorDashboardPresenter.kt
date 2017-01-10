package com.jenshen.smartmirror.ui.mvp.presenter.dashboard.mirror

import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo
import com.jenshen.smartmirror.data.model.widget.PrecipitationModel
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.data.updater.WidgetUpdater
import com.jenshen.smartmirror.data.updater.weather.CurrentWeatherUpdater
import com.jenshen.smartmirror.interactor.firebase.api.mirror.MirrorApiInteractor
import com.jenshen.smartmirror.manager.widget.factory.IWidgetFactoryManager
import com.jenshen.smartmirror.ui.mvp.view.dashboard.mirror.MirrorDashboardView
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MirrorDashboardPresenter @Inject constructor(private val mirrorApiInteractor: MirrorApiInteractor,
                                                   private val widgetFactoryManager: IWidgetFactoryManager) :
        MvpRxPresenter<MirrorDashboardView>() {
    private val updaterList: MutableList<WidgetUpdater<*>> = mutableListOf()
    private var userInfoFlagDisposable: Disposable? = null
    private var precipitationFlagDisposable: Disposable? = null
    private var precipitationDisposable: Disposable? = null
    private var screenOrientationDisposable: Disposable? = null

    override fun attachView(view: MirrorDashboardView?) {
        super.attachView(view)
        fetchIsNeedToShowQrCode()
        fetchSelectedConfiguration()
    }

    fun addWidgetUpdater(widgetKey: WidgetKey) {
        val updater = widgetFactoryManager.getUpdaterForWidget(widgetKey)
        updaterList.add(updater)
        addDisposible(updater.startUpdate()
                .applySchedulers(Schedulers.io())
                .subscribe({ view?.onWidgetUpdate(it) }, { view?.handleError(it) }))
    }

    fun updateWidget(infoData: WidgetData, widget: Widget<*>) {
        widgetFactoryManager.updateWidget(infoData, widget)
    }

    fun enablePrecipitation(enable: Boolean) {
        if (enable) {
            val updaterForWidget: CurrentWeatherUpdater = widgetFactoryManager.getUpdaterForWidget(WidgetKey(FirebaseRealTimeDatabaseConstant.Widget.CURRENT_WEATHER_WIDGET_KEY)) as CurrentWeatherUpdater
            precipitationDisposable = updaterForWidget
                    .startUpdate()
                    .map { it.weatherResponse }
                    .filter { it.weathersList != null && it.weathersList.isNotEmpty() }
                    .map { it.weathersList!!.iterator().next().id }
                    .map { PrecipitationModel.createFromWeatherData(it) }
                    .applySchedulers(Schedulers.io())
                    .subscribe({ view?.onPrecipitationUpdate(it) }, { view?.handleError(it) })
            addDisposible(precipitationDisposable)
        } else {
            if (precipitationDisposable != null && !precipitationDisposable!!.isDisposed) {
                compositeDisposable.remove(precipitationDisposable)
                precipitationDisposable = null
            }
        }
    }

    /* private methods */

    private fun fetchIsNeedToShowQrCode() {
        addDisposible(mirrorApiInteractor.fetchIsNeedToShowQrCode()
                .applySchedulers(Schedulers.io())
                .subscribe({ view?.showSignUpScreen() }, { view?.handleError(it) }))
    }

    private fun fetchSelectedConfiguration() {
        addDisposible(mirrorApiInteractor.fetchSelectedMirrorConfiguration()
                .applySchedulers(Schedulers.io())
                .subscribe({
                    clearWidgetsUpdaters()
                    view?.changeMirrorConfiguration(it.data)
                    onConfigurationChanged(it.key)
                }, { view?.handleError(it) }))
    }

    private fun onConfigurationChanged(configurationKey: String) {
        if (precipitationFlagDisposable != null && !precipitationFlagDisposable!!.isDisposed) {
            compositeDisposable.remove(precipitationFlagDisposable)
            precipitationFlagDisposable = null
        }

        if (userInfoFlagDisposable != null && !userInfoFlagDisposable!!.isDisposed) {
            compositeDisposable.remove(userInfoFlagDisposable)
            userInfoFlagDisposable = null
        }

        if (screenOrientationDisposable != null && !screenOrientationDisposable!!.isDisposed) {
            compositeDisposable.remove(screenOrientationDisposable)
            screenOrientationDisposable = null
        }
        fetchNeedToShowUserInfo(configurationKey)
        fetchIsEnablePrecipitation(configurationKey)
        fetchScreenOrientationPrecipitation(configurationKey)
    }

    private fun fetchNeedToShowUserInfo(configurationKey: String) {
        val disposable = mirrorApiInteractor.fetchIsNeedToShowUserInfo(configurationKey)
                .applySchedulers(Schedulers.io())
                .subscribe({ view?.enableUserInfo(it) }, { view?.handleError(it) })
        userInfoFlagDisposable = disposable
        addDisposible(disposable)
    }

    private fun fetchIsEnablePrecipitation(configurationKey: String) {
        val disposable = mirrorApiInteractor.fetchEnablePrecipitation(configurationKey)
                .applySchedulers(Schedulers.io())
                .subscribe({ view?.enablePrecipitation(it) }, { view?.handleError(it) })
        precipitationFlagDisposable = disposable
        addDisposible(disposable)
    }

    private fun fetchScreenOrientationPrecipitation(configurationKey: String) {
        val disposable = mirrorApiInteractor.fetchScreenOrientation(configurationKey)
                .applySchedulers(Schedulers.io())
                .subscribe({ view?.onChangeOrientation(it) }, { view?.handleError(it) })
        screenOrientationDisposable = disposable
        addDisposible(disposable)
    }

    private fun clearWidgetsUpdaters() {
        updaterList.forEach { it.apply { clear() } }
        updaterList.clear()
    }
}