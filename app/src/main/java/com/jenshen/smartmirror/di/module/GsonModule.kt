package com.jenshen.smartmirror.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class GsonModule {

    @Provides
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(Date::class.java, JsonDeserializer<Date>
        { json, typeOfT, context -> Date(json.asJsonPrimitive.asLong * 1000) })

        return builder.create()
    }
}