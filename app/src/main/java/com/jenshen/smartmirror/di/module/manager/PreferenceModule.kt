package com.jenshen.smartmirror.di.module.manager

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.jenshen.smartmirror.di.module.GsonModule
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.manager.preference.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(GsonModule::class))
class PreferenceModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun providePreferencesManager(context: Context, sharedPreferences: SharedPreferences, gson: Gson): PreferencesManager {
        return SharedPreferencesManager(context, sharedPreferences, gson)
    }
}