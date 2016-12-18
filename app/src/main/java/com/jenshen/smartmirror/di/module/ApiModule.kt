package com.jenshen.smartmirror.di.module

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jenshen.smartmirror.BuildConfig
import com.jenshen.smartmirror.di.scope.ApiScope
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = arrayOf(GsonModule::class))
class ApiModule(private val baseUrl: String) {

    @ApiScope
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @ApiScope
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

    @ApiScope
    @Provides
    fun provideOkHttpClient(headersInterceptor: Interceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headersInterceptor)
                .build()
        return client
    }

    @ApiScope
    @Provides
    fun provideRestAdapter(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build()
    }
}