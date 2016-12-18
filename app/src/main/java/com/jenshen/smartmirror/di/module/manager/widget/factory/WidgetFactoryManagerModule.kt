package com.jenshen.smartmirror.di.module.manager.widget.factory

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.jenshen.smartmirror.data.api.WeatherApi
import com.jenshen.smartmirror.di.module.GsonModule
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.api.IWeatherApiManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.manager.preference.SharedPreferencesManager
import com.jenshen.smartmirror.manager.widget.factory.IWidgetFactoryManager
import com.jenshen.smartmirror.manager.widget.factory.WidgetFactoryManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WidgetFactoryManagerModule {

    @SessionScope
    @Provides
    fun provideWidgetFactoryManager( weatherApi: IWeatherApiManager): IWidgetFactoryManager {
        return WidgetFactoryManager(weatherApi)
    }
}