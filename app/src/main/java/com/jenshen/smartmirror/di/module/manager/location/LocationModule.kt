package com.jenshen.smartmirror.di.module.manager.location

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.jenshen.smartmirror.di.module.GsonModule
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.location.FindLocationManager
import com.jenshen.smartmirror.manager.location.IFindLocationManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.manager.preference.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule {

    @SessionScope
    @Provides
    fun provideSharedPreferences(context: Context): IFindLocationManager {
        return FindLocationManager(context)
    }
}