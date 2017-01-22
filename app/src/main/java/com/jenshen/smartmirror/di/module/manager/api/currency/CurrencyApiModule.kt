package com.jenshen.smartmirror.di.module.manager.api.currency

import com.jenshen.smartmirror.data.api.currency.CurrencyApi
import com.jenshen.smartmirror.di.component.ApiComponent
import com.jenshen.smartmirror.di.module.ApiModule
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.api.currency.CurrencyApiManager
import com.jenshen.smartmirror.manager.api.currency.ICurrencyApiManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor

@Module(subcomponents = arrayOf(ApiComponent::class))
class CurrencyApiModule {

    @SessionScope
    @Provides
    fun provideCurrencyApi(apiComponentBuilder: ApiComponent.Builder): CurrencyApi {
        return apiComponentBuilder
                .apiModule(ApiModule(CurrencyApi.API_URL))
                .build()
                .provideRetrofit()
                .create(CurrencyApi::class.java)
    }

    @SessionScope
    @Provides
    fun provideCurrencyApiManager(api: CurrencyApi, referencesManager: PreferencesManager): ICurrencyApiManager {
        return CurrencyApiManager(api, referencesManager)
    }
}
