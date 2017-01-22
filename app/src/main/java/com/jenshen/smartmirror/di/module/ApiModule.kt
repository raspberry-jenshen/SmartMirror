package com.jenshen.smartmirror.di.module

import android.text.format.DateUtils
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jenshen.smartmirror.di.scope.ApiScope
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module(includes = arrayOf(GsonModule::class))
class ApiModule(private val baseUrl: String,
                private val interceptor: Interceptor? = null,
                private val timeOutTime: Long = DateUtils.MINUTE_IN_MILLIS * 10) {

    @ApiScope
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @ApiScope
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
        if (interceptor != null) {
            builder.addInterceptor(interceptor)
        }
        builder.connectTimeout(timeOutTime, TimeUnit.MILLISECONDS)
                .readTimeout(timeOutTime, TimeUnit.MILLISECONDS)
                .writeTimeout(timeOutTime, TimeUnit.MILLISECONDS)
        return builder.build()
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