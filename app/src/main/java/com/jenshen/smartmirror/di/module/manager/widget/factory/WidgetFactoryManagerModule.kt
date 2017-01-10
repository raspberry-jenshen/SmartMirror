package com.jenshen.smartmirror.di.module.manager.widget.factory

import android.content.Context
import com.jenshen.smartmirror.di.module.manager.api.currency.CurrencyApiModule
import com.jenshen.smartmirror.di.module.manager.api.weather.WeatherApiModule
import com.jenshen.smartmirror.di.module.manager.calendar.CalendarManagerModule
import com.jenshen.smartmirror.di.module.manager.location.LocationModule
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.api.currency.ICurrencyApiManager
import com.jenshen.smartmirror.manager.api.weather.IWeatherApiManager
import com.jenshen.smartmirror.manager.calendar.ICalendarManager
import com.jenshen.smartmirror.manager.location.IFindLocationManager
import com.jenshen.smartmirror.manager.widget.factory.IWidgetFactoryManager
import com.jenshen.smartmirror.manager.widget.factory.WidgetFactoryManager
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(
        CalendarManagerModule::class,
        LocationModule::class,
        //api
        WeatherApiModule::class,
        CurrencyApiModule::class))
class WidgetFactoryManagerModule {

    @SessionScope
    @Provides
    fun provideCalendarManager(context: Context,
                               currencyApiManager: dagger.Lazy<ICurrencyApiManager>,
                               weatherApiLazy: dagger.Lazy<IWeatherApiManager>,
                               findLocationManagerLazy: dagger.Lazy<IFindLocationManager>,
                               calendarManager: dagger.Lazy<ICalendarManager>): IWidgetFactoryManager {
        return WidgetFactoryManager(context, currencyApiManager, weatherApiLazy, findLocationManagerLazy, calendarManager)
    }
}