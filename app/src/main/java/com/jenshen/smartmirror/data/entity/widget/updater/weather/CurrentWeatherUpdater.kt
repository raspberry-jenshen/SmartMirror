package com.jenshen.smartmirror.data.entity.widget.updater.weather

import android.Manifest
import android.content.Context
import android.support.annotation.RequiresPermission
import com.jenshen.smartmirror.data.entity.widget.info.weather.CurrentWeatherWidgetData
import com.jenshen.smartmirror.data.entity.widget.updater.WidgetUpdater
import com.jenshen.smartmirror.data.model.widget.MirrorLocationModel
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.manager.api.IWeatherApiManager
import com.jenshen.smartmirror.manager.location.IFindLocationManager
import dagger.Lazy
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CurrentWeatherUpdater(widgetKey: WidgetKey,
                            private val context: Context,
                            private val weatherApiLazy: Lazy<IWeatherApiManager>,
                            private val findLocationManagerLazy: Lazy<IFindLocationManager>) : WidgetUpdater<CurrentWeatherWidgetData>(widgetKey) {

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun startUpdate(): Observable<CurrentWeatherWidgetData> {

        return Observable.interval(0, 3, TimeUnit.HOURS)
                .takeWhile { !isDisposed }
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
                .flatMapSingle { weatherApiLazy.get().getCurrentWeather(it.lat, it.lon) }
                .map {
                    CurrentWeatherWidgetData(widgetKey, it)
                }
    }
}