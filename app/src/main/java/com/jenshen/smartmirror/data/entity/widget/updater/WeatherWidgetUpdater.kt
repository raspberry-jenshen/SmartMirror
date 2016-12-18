package com.jenshen.smartmirror.data.entity.widget.updater

import com.jenshen.smartmirror.data.entity.widget.info.WeatherWidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.manager.api.IWeatherApiManager
import io.reactivex.Observable
import java.util.*
import java.util.Calendar.*
import java.util.concurrent.TimeUnit


class WeatherWidgetUpdater(widgetKey: WidgetKey, weatherApi: IWeatherApiManager) : WidgetUpdater<WeatherWidgetData>(widgetKey) {

    override fun startUpdate(): Observable<WeatherWidgetData> {

        return Observable.interval(0, 3, TimeUnit.HOURS)
                .takeWhile { !isDisposed }
                .map {
                    val calendar = Calendar.getInstance()
                    WeatherWidgetData(widgetKey, calendar.get(HOUR), calendar.get(MINUTE), calendar.get(SECOND))
                }
    }
}