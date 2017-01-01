package com.jenshen.smartmirror.di.module

import com.google.gson.*
import dagger.Module
import dagger.Provides
import java.lang.reflect.Type
import java.util.*

@Module
class GsonModule {

    @Provides
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(Date::class.java, JsonDeserializer<Date>
        { json, typeOfT, context -> Date(json.asJsonPrimitive.asLong * 1000) })
        builder.registerTypeAdapter(Date::class.java, JsonSerializer<Date>
        { date: Date, type, context -> JsonPrimitive(date.time / 1000)})

        return builder.create()
    }
}