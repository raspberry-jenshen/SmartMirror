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
class AppModule(private var context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }
}