package com.jenshen.smartmirror.manager.widget.factory

import android.content.Context
import android.util.Log
import com.jenshen.smartmirror.data.entity.widget.info.CalendarEventsWidgetData
import com.jenshen.smartmirror.data.entity.widget.info.ClockWidgetData
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.entity.widget.info.currency.ExchangeRatesWidgetData
import com.jenshen.smartmirror.data.entity.widget.info.weather.CurrentWeatherWidgetData
import com.jenshen.smartmirror.data.entity.widget.info.weather.WeatherForecastWidgetData
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.data.updater.CalendarEventsUpdater
import com.jenshen.smartmirror.data.updater.ClockUpdater
import com.jenshen.smartmirror.data.updater.WidgetUpdater
import com.jenshen.smartmirror.data.updater.currency.ExchangeRatesUpdater
import com.jenshen.smartmirror.data.updater.weather.CurrentWeatherUpdater
import com.jenshen.smartmirror.data.updater.weather.WeatherForecastUpdater
import com.jenshen.smartmirror.manager.api.currency.ICurrencyApiManager
import com.jenshen.smartmirror.manager.api.weather.IWeatherApiManager
import com.jenshen.smartmirror.manager.calendar.ICalendarManager
import com.jenshen.smartmirror.manager.location.IFindLocationManager
import com.jenshen.smartmirror.ui.view.widget.CalendarEventsView
import com.jenshen.smartmirror.ui.view.widget.ClockView
import com.jenshen.smartmirror.ui.view.widget.ExchangeRatesView
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshen.smartmirror.ui.view.widget.weather.CurrentWeatherView
import com.jenshen.smartmirror.ui.view.widget.weather.WeatherForecastForDayView
import com.jenshen.smartmirror.ui.view.widget.weather.WeatherForecastForWeekView


class WidgetFactoryManager constructor(private val context: Context,
                                       private val currencyApiManager: dagger.Lazy<ICurrencyApiManager>,
                                       private val weatherApiLazy: dagger.Lazy<IWeatherApiManager>,
                                       private val findLocationManagerLazy: dagger.Lazy<IFindLocationManager>,
                                       private val calendarManager: dagger.Lazy<ICalendarManager>) : IWidgetFactoryManager {

    override fun getUpdaterForWidget(widgetKey: WidgetKey): WidgetUpdater<*> {
        return when (widgetKey.key) {
            FirebaseRealTimeDatabaseConstant.Widget.CLOCK_WIDGET_KEY -> {
                ClockUpdater(widgetKey)
            }
            FirebaseRealTimeDatabaseConstant.Widget.CURRENT_WEATHER_WIDGET_KEY -> {
                CurrentWeatherUpdater(widgetKey, context, weatherApiLazy, findLocationManagerLazy)
            }
            FirebaseRealTimeDatabaseConstant.Widget.WEATHER_FORECAST_FOR_DAY_WIDGET_KEY, FirebaseRealTimeDatabaseConstant.Widget.WEATHER_FORECAST_FOR_WEEK_WIDGET_KEY -> {
                WeatherForecastUpdater(widgetKey, context, weatherApiLazy, findLocationManagerLazy)
            }
            FirebaseRealTimeDatabaseConstant.Widget.EXCHANGE_RATES_WIDGET_KEY -> {
                ExchangeRatesUpdater(widgetKey, currencyApiManager.get())
            }
            FirebaseRealTimeDatabaseConstant.Widget.CALENDAR_EVENTS_WIDGET_KEY -> {
                CalendarEventsUpdater(widgetKey, calendarManager.get())
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
        } else if (widget is ExchangeRatesView && infoData is ExchangeRatesWidgetData) {
            widget.updateWidget(infoData)
            return
        } else if (widget is CalendarEventsView && infoData is CalendarEventsWidgetData) {
            widget.updateWidget(infoData)
            return
        }
        Log.e("SmartMirror", "Can't support this widget")
    }
}
