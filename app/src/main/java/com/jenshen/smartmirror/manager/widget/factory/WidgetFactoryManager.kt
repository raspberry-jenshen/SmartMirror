package com.jenshen.smartmirror.manager.widget.factory

import android.content.Context
import com.jenshen.smartmirror.data.entity.widget.info.ClockWidgetData
import com.jenshen.smartmirror.data.entity.widget.info.CurrentWeatherWidgetData
import com.jenshen.smartmirror.data.entity.widget.info.WeatherForecastWidgetData
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.entity.widget.updater.ClockWidgetUpdater
import com.jenshen.smartmirror.data.entity.widget.updater.CurrentWeatherWidgetUpdater
import com.jenshen.smartmirror.data.entity.widget.updater.WeatherForecastWidgetUpdater
import com.jenshen.smartmirror.data.entity.widget.updater.WidgetUpdater
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.manager.api.IWeatherApiManager
import com.jenshen.smartmirror.manager.location.IFindLocationManager
import com.jenshen.smartmirror.ui.view.widget.ClockView
import com.jenshen.smartmirror.ui.view.widget.CurrentWeatherView
import com.jenshen.smartmirror.ui.view.widget.WeatherForecastView
import com.jenshen.smartmirror.ui.view.widget.Widget
import javax.inject.Inject


class WidgetFactoryManager @Inject constructor(private val context: Context,
                                               private val weatherApiLazy: dagger.Lazy<IWeatherApiManager>,
                                               private val findLocationManagerLazy: dagger.Lazy<IFindLocationManager>) : IWidgetFactoryManager {

    override fun getUpdaterForWidget(widgetKey: WidgetKey): WidgetUpdater<*> {
        return when (widgetKey.key) {
            WidgetInfo.CLOCK_WIDGET_KEY -> {
                ClockWidgetUpdater(widgetKey)
            }
            WidgetInfo.CURRENT_WEATHER_WIDGET_KEY -> {
                CurrentWeatherWidgetUpdater(widgetKey, context, weatherApiLazy, findLocationManagerLazy)
            }
            WidgetInfo.WEATHER_FORECAST_WIDGET_KEY -> {
                WeatherForecastWidgetUpdater(widgetKey, context, weatherApiLazy, findLocationManagerLazy)
            }
            else -> throw RuntimeException("Can't support this widget")
        }
    }

    override fun updateWidget(infoData: WidgetData, widget: Widget<*>) {
        if (widget is ClockView && infoData is ClockWidgetData) {
            widget.updateWidget(infoData)
        } else if (widget is CurrentWeatherView && infoData is CurrentWeatherWidgetData) {
            widget.updateWidget(infoData)
        } else if (widget is WeatherForecastView && infoData is WeatherForecastWidgetData) {
            widget.updateWidget(infoData)
        }
    }
}
