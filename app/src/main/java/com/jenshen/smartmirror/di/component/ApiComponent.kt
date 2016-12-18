package com.jenshen.smartmirror.di.component

import com.jenshen.smartmirror.di.module.ApiModule
import com.jenshen.smartmirror.di.scope.ApiScope
import dagger.Subcomponent
import retrofit2.Retrofit

@ApiScope
@Subcomponent(modules = arrayOf(ApiModule::class))
interface ApiComponent {

    fun provideRetrofit(): Retrofit

    @Subcomponent.Builder
    interface Builder {
        fun apiModule(module: ApiModule): Builder
        fun build(): ApiComponent
    }
}
