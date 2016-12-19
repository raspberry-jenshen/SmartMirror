package com.jenshen.smartmirror.data.entity.widget.updater

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.annotation.RequiresPermission
import android.support.v4.content.ContextCompat
import com.jenshen.smartmirror.data.api.WeatherApi.Companion.IMAGE_PATH_URL
import com.jenshen.smartmirror.data.entity.widget.info.CurrentWeatherWidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.manager.api.IWeatherApiManager
import com.jenshen.smartmirror.manager.location.IFindLocationManager
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CurrentWeatherWidgetUpdater(widgetKey: WidgetKey,
                                  private val context: Context,
                                  private val weatherApiLazy: dagger.Lazy<IWeatherApiManager>,
                                  private val findLocationManagerLazy: dagger.Lazy<IFindLocationManager>) : WidgetUpdater<CurrentWeatherWidgetData>(widgetKey) {

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun startUpdate(): Observable<CurrentWeatherWidgetData> {

        return Observable.interval(0, 3, TimeUnit.HOURS)
                .takeWhile { !isDisposed }
                .flatMap {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Single.fromCallable { findLocationManagerLazy.get() }
                                .observeOn(AndroidSchedulers.mainThread())
                                .flatMapObservable { it.fetchCurrentLocation() }
                                .observeOn(Schedulers.io())
                                .map { MirrorLocation(it.latitude, it.longitude) }
                    } else {
                        Observable.fromCallable { MirrorLocation() }
                    }
                }
                .flatMapSingle { weatherApiLazy.get().getCurrentWeather(it.lat, it.lon) }
                .map {
                    CurrentWeatherWidgetData(widgetKey, it)
                }
    }

    //new york by default
    private class MirrorLocation(val lat: Double = 40.730610,
                                 val lon: Double = -73.935242)
}