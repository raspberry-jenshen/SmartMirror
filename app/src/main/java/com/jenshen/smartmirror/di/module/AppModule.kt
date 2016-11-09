package com.jenshen.smartmirror.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.jenshen.smartmirror.manager.preference.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import java.util.*
import javax.inject.Singleton

@Singleton
@Module
class AppModule {

    private var context: Context

    constructor(context: Context) {
        this.context = context
    }

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(Date::class.java, JsonDeserializer<Date>
        { json, typeOfT, context -> Date(json.asJsonPrimitive.asLong * 1000) })

        return builder.create()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun providePreferencesManager(sharedPreferences: SharedPreferences, gson: Gson): PreferencesManager {
        return SharedPreferencesManager(context, sharedPreferences, gson)
    }
}