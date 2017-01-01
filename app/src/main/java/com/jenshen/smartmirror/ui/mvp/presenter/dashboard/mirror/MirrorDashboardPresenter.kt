package com.jenshen.smartmirror.ui.mvp.presenter.dashboard.mirror

import android.content.Context
import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.entity.widget.updater.WidgetUpdater
import com.jenshen.smartmirror.data.model.widget.MirrorLocationModel
import com.jenshen.smartmirror.data.model.widget.PrecipitationModel
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.interactor.firebase.api.mirror.MirrorApiInteractor
import com.jenshen.smartmirror.manager.api.weather.IWeatherApiManager
import com.jenshen.smartmirror.manager.location.IFindLocationManager
import com.jenshen.smartmirror.manager.widget.factory.WidgetFactoryManager
import com.jenshen.smartmirror.ui.mvp.view.dashboard.mirror.MirrorDashboardView
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MirrorDashboardPresenter @Inject constructor(private val context: Context,
                                                   private val mirrorApiInteractor: MirrorApiInteractor,
                                                   private val weatherApiManagerLazy: dagger.Lazy<IWeatherApiManager>,
                                                   private val findLocationManagerLazy: dagger.Lazy<IFindLocationManager>,
                                                   private val widgetFactoryManager: WidgetFactoryManager) :
        MvpRxPresenter<MirrorDashboardView>() {
    private val updaterList: MutableList<WidgetUpdater<*>> = mutableListOf()
    private var userInfoFlagDisposable: Disposable? = null
    private var precipitationFlagDisposable: Disposable? = null
    private var precipitationDisposable: Disposable? = null

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

    fun updateWidget(infoData: WidgetData, widget: Widget<*>) {
        widgetFactoryManager.updateWidget(infoData, widget)
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
        fetchNeedToShowUserInfo(configurationKey)
        fetchIsEnablePrecipitation(configurationKey)
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
                .subscribe({ enablePrecipitation(it) }, { view?.handleError(it) })
        precipitationFlagDisposable = disposable
        addDisposible(disposable)
    }

    private fun clearWidgetsUpdaters() {
        updaterList.forEach { it.apply { clear() } }
        updaterList.clear()
    }

    private fun enablePrecipitation(enable: Boolean) {
        if (enable) {
            Observable.interval(0, 3, TimeUnit.HOURS)
                    .flatMap {
                        if (IFindLocationManager.canGetLocation(context)) {
                            Single.fromCallable { findLocationManagerLazy.get() }
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .flatMapObservable { it.fetchCurrentLocation(1000000, 1000000) }
                                    .observeOn(Schedulers.io())
                                    .map { MirrorLocationModel(it.latitude, it.longitude) }
                        } else {
                            Observable.fromCallable { MirrorLocationModel() }
                        }
                    }
                    .flatMapSingle { weatherApiManagerLazy.get().getCurrentWeather(it.lat, it.lon) }
                    .filter { it.weathersList != null && it.weathersList.isNotEmpty() }
                    .map { it.weathersList!!.iterator().next().id }
                    .map { PrecipitationModel.createFromWeatherData(it) }
                    .applySchedulers(Schedulers.io())
                    .doOnSubscribe {
                        precipitationDisposable = it
                        compositeDisposable.add(it)
                    }
                    .subscribe({ view?.onPrecipitationUpdate(it) }, { view?.handleError(it) })
        } else {
            if (precipitationDisposable != null && !precipitationDisposable!!.isDisposed) {
                compositeDisposable.remove(precipitationDisposable)
                precipitationDisposable = null
            }
        }
    }
}