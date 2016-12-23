package com.jenshen.smartmirror.ui.mvp.presenter.dashboard.mirror

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.entity.widget.updater.WidgetUpdater
import com.jenshen.smartmirror.data.model.widget.MirrorLocationModel
import com.jenshen.smartmirror.data.model.widget.PrecipitationModel
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.interactor.firebase.api.mirror.MirrorApiInteractor
import com.jenshen.smartmirror.manager.api.IWeatherApiManager
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
    private val updaterList: MutableList<WidgetUpdater<*>>
    private var precipitationDisposable: Disposable? = null

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

    fun updateWidget(infoData: WidgetData, widget: Widget<*>) {
        widgetFactoryManager.updateWidget(infoData, widget)
    }

    fun enablePrecipitation(enable: Boolean) {
        if (enable) {
            Observable.interval(0, 3, TimeUnit.HOURS)
                    .flatMap {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            Single.fromCallable { findLocationManagerLazy.get() }
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .flatMapObservable { it.fetchCurrentLocation() }
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
                        compositeDisposable.add(it) }
                    .subscribe({ view?.onPrecipitationUpdate(it) }, { view?.handleError(it) })
        } else {
            if (precipitationDisposable != null) {
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

    private fun fetchNeedToShowUserInfo(configurationKey: String) {
        addDisposible(mirrorApiInteractor.fetchIsNeedToShowUserInfo(configurationKey)
                .applySchedulers(Schedulers.io())
                .subscribe({ view?.enableUserInfo(it) }, { view?.handleError(it) }))
    }

    private fun fetchIsEnablePrecipitation(configurationKey: String) {
        addDisposible(mirrorApiInteractor.fetchEnablePrecipitation(configurationKey)
                .applySchedulers(Schedulers.io())
                .subscribe({ view?.enablePrecipitation(it) }, { view?.handleError(it) }))
    }

    private fun onConfigurationChanged(configurationKey: String) {
        fetchNeedToShowUserInfo(configurationKey)
        fetchIsEnablePrecipitation(configurationKey)
    }

    private fun clearWidgetsUpdaters() {
        updaterList.forEach { it.apply { clear() } }
        updaterList.clear()
    }
}