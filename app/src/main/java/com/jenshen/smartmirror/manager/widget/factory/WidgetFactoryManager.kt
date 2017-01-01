package com.jenshen.smartmirror.manager.widget.factory

import android.content.Context
import com.jenshen.smartmirror.data.entity.widget.info.ClockWidgetData
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.entity.widget.info.weather.CurrentWeatherWidgetData
import com.jenshen.smartmirror.data.entity.widget.info.weather.WeatherForecastWidgetData
import com.jenshen.smartmirror.data.entity.widget.updater.ClockUpdater
import com.jenshen.smartmirror.data.entity.widget.updater.WidgetUpdater
import com.jenshen.smartmirror.data.entity.widget.updater.weather.CurrentWeatherUpdater
import com.jenshen.smartmirror.data.entity.widget.updater.weather.WeatherForecastUpdater
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.manager.api.weather.IWeatherApiManager
import com.jenshen.smartmirror.manager.location.IFindLocationManager
import com.jenshen.smartmirror.ui.view.widget.*
import javax.inject.Inject


class WidgetFactoryManager @Inject constructor(private val context: Context,
                                               private val weatherApiLazy: dagger.Lazy<IWeatherApiManager>,
                                               private val findLocationManagerLazy: dagger.Lazy<IFindLocationManager>) : IWidgetFactoryManager {

    override fun getUpdaterForWidget(widgetKey: WidgetKey): WidgetUpdater<*> {
        return when (widgetKey.key) {
            WidgetInfo.CLOCK_WIDGET_KEY -> {
                ClockUpdater(widgetKey)
            }
            WidgetInfo.CURRENT_WEATHER_WIDGET_KEY -> {
                CurrentWeatherUpdater(widgetKey, context, weatherApiLazy, findLocationManagerLazy)
            }
            WidgetInfo.WEATHER_FORECAST_FOR_DAY_WIDGET_KEY, WidgetInfo.WEATHER_FORECAST_FOR_WEEK_WIDGET_KEY -> {
                WeatherForecastUpdater(widgetKey, context, weatherApiLazy, findLocationManagerLazy)
            }
            WidgetInfo.EXCHANGE_RATES_WIDGET_KEY -> {
                CurrentWeatherUpdater(widgetKey, context, weatherApiLazy, findLocationManagerLazy)
            }
            else -> throw RuntimeException("Can't support this widget")
        }
    }

    override fun updateWidget(infoData: WidgetData, widget: Widget<*>) {
        if (widget is ClockView && infoData is ClockWidgetData) {
            widget.updateWidget(infoData)
            return
        } else if (widget is CurrentWeatherView && infoData is CurrentWeatherWidgetData) {
            widget.updateWidget(infoData)
            return
        } else if (infoData is WeatherForecastWidgetData) {
            if (widget is WeatherForecastForDayView) {
                widget.updateWidget(infoData)
                return
            } else if (widget is WeatherForecastForWeekView) {
                widget.updateWidget(infoData)
                return
            }
        }
        throw RuntimeException("Can't support this widget")
    }
}
