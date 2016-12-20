package com.jenshen.smartmirror.di.module.manager.api

import com.jenshen.smartmirror.BuildConfig
import com.jenshen.smartmirror.data.api.WeatherApi
import com.jenshen.smartmirror.di.component.ApiComponent
import com.jenshen.smartmirror.di.module.ApiModule
import com.jenshen.smartmirror.di.scope.ApiScope
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.api.IWeatherApiManager
import com.jenshen.smartmirror.manager.api.WeatherApiManager
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor

@Module(subcomponents = arrayOf(ApiComponent::class))
class WeatherApiModule {

    @SessionScope
    @Provides
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
            request.addHeader("Content-type", "application/json")
            request.addHeader("x-api-key", BuildConfig.WEATHER_API_KEY)
            chain.proceed(request.method(original.method(), original.body()).build())
        }
    }

    @SessionScope
    @Provides
    fun provideWeatherApi(apiComponentBuilder: ApiComponent.Builder, interceptor: Interceptor): WeatherApi {
        return apiComponentBuilder
                .apiModule(ApiModule(WeatherApi.API_URL, interceptor))
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
