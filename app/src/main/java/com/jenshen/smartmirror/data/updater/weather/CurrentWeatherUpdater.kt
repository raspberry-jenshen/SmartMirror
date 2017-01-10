package com.jenshen.smartmirror.data.updater.weather

import android.Manifest
import android.content.Context
import android.support.annotation.RequiresPermission
import com.jenshen.smartmirror.data.entity.widget.info.weather.CurrentWeatherWidgetData
import com.jenshen.smartmirror.data.updater.WidgetUpdater
import com.jenshen.smartmirror.data.model.widget.MirrorLocationModel
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.manager.api.weather.IWeatherApiManager
import com.jenshen.smartmirror.manager.location.IFindLocationManager
import dagger.Lazy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CurrentWeatherUpdater(widgetKey: WidgetKey,
                            private val context: Context,
                            private val weatherApiLazy: Lazy<IWeatherApiManager>,
                            private val findLocationManagerLazy: Lazy<IFindLocationManager>) : WidgetUpdater<CurrentWeatherWidgetData>(widgetKey) {

    override val initialDelay: Long = 0
    override val period: Long = MINUTES_BETWEEN_UPDATES * 1000

    companion object {
        const val MINUTES_BETWEEN_UPDATES = 60L
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun getInfo(): Flowable<CurrentWeatherWidgetData> {
        val single: Single<MirrorLocationModel>
        if (IFindLocationManager.canGetLocation(context)) {
            single = Single.fromCallable { findLocationManagerLazy.get() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap { it.getCurrentLocation(1000000, 1000000) }
                    .observeOn(Schedulers.io())
                    .map { MirrorLocationModel(it.latitude, it.longitude) }
        } else {
            single = Single.fromCallable { MirrorLocationModel() }
        }
        return single
                .flatMapPublisher { weatherApiLazy.get().getCurrentWeather(it.lat, it.lon) }
                .map { CurrentWeatherWidgetData(widgetKey, it) }
    }
}