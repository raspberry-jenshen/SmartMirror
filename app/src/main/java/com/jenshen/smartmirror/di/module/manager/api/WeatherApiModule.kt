package com.jenshen.smartmirror.di.module.manager.api

import com.jenshen.smartmirror.data.api.WeatherApi
import com.jenshen.smartmirror.di.component.ApiComponent
import com.jenshen.smartmirror.di.module.ApiModule
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.api.IWeatherApiManager
import com.jenshen.smartmirror.manager.api.WeatherApiManager
import dagger.Module
import dagger.Provides

@Module(subcomponents = arrayOf(ApiComponent::class))
class WeatherApiModule {

    @SessionScope
    @Provides
    fun provideWeatherApi(apiComponentBuilder: ApiComponent.Builder): WeatherApi {
        return apiComponentBuilder
                .apiModule(ApiModule(WeatherApi.API_URL))
                .build()
                .provideRetrofit()
                .create(WeatherApi::class.java)
    }

    @SessionScope
    @Provides
    fun provideWeatherApiManager(api: WeatherApi): IWeatherApiManager {
        return WeatherApiManager(api)
    }
}
