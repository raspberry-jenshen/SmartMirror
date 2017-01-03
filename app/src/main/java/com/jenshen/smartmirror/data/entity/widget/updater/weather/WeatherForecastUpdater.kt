package com.jenshen.smartmirror.data.entity.widget.updater.weather

import android.Manifest
import android.content.Context
import android.support.annotation.RequiresPermission
import com.jenshen.smartmirror.data.entity.widget.info.weather.WeatherForecastWidgetData
import com.jenshen.smartmirror.data.entity.widget.updater.WidgetUpdater
import com.jenshen.smartmirror.data.model.widget.MirrorLocationModel
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.manager.api.weather.IWeatherApiManager
import com.jenshen.smartmirror.manager.location.IFindLocationManager
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class WeatherForecastUpdater(widgetKey: WidgetKey,
                             private val context: Context,
                             private val weatherApiLazy: dagger.Lazy<IWeatherApiManager>,
                             private val findLocationManagerLazy: dagger.Lazy<IFindLocationManager>) : WidgetUpdater<WeatherForecastWidgetData>(widgetKey) {

    companion object {
        const val HOURS_BETWEEN_UPDATES = 3L
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun startUpdate(): Flowable<WeatherForecastWidgetData> {

        return Flowable.interval(0, HOURS_BETWEEN_UPDATES, TimeUnit.HOURS)
                .takeWhile { !isDisposed }
                .flatMap {
                    if (IFindLocationManager.canGetLocation(context)) {
                        Single.fromCallable { findLocationManagerLazy.get() }
                                .observeOn(AndroidSchedulers.mainThread())
                                .flatMapPublisher { it.fetchCurrentLocation(1000000, 1000000) }
                                .observeOn(Schedulers.io())
                                .map { MirrorLocationModel(it.latitude, it.longitude) }
                    } else {
                        Flowable.fromCallable { MirrorLocationModel() }
                    }
                }
                .flatMap { weatherApiLazy.get().getWeatherForecast(it.lat, it.lon) }
                .map { WeatherForecastWidgetData(widgetKey, it) }
    }
}