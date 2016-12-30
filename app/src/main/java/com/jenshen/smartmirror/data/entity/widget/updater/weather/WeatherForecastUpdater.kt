package com.jenshen.smartmirror.data.entity.widget.updater.weather

import android.Manifest
import android.content.Context
import android.support.annotation.RequiresPermission
import com.jenshen.smartmirror.data.entity.widget.info.weather.WeatherForecastWidgetData
import com.jenshen.smartmirror.data.entity.widget.updater.WidgetUpdater
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.manager.api.IWeatherApiManager
import com.jenshen.smartmirror.manager.location.IFindLocationManager
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class WeatherForecastUpdater(widgetKey: WidgetKey,
                             private val context: Context,
                             private val weatherApiLazy: dagger.Lazy<IWeatherApiManager>,
                             private val findLocationManagerLazy: dagger.Lazy<IFindLocationManager>) : WidgetUpdater<WeatherForecastWidgetData>(widgetKey) {

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun startUpdate(): Observable<WeatherForecastWidgetData> {

        return Observable.interval(0, 3, TimeUnit.HOURS)
                .takeWhile { !isDisposed }
                .flatMap {
                    if (IFindLocationManager.canGetLocation(context)) {
                        Single.fromCallable { findLocationManagerLazy.get() }
                                .observeOn(AndroidSchedulers.mainThread())
                                .flatMapObservable { it.fetchCurrentLocation(1000000, 1000000) }
                                .observeOn(Schedulers.io())
                                .map { MirrorLocation(it.latitude, it.longitude) }
                    } else {
                        Observable.fromCallable { MirrorLocation() }
                    }
                }
                .flatMapSingle { weatherApiLazy.get().getWeatherForecast(it.lat, it.lon) }
                .map {
                    WeatherForecastWidgetData(widgetKey, it)
                }
    }

    //new york by default
    private class MirrorLocation(val lat: Double = 40.730610,
                                 val lon: Double = -73.935242)
}