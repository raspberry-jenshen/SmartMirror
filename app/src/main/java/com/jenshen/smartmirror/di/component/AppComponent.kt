package com.jenshen.smartmirror.di.component

import android.app.Activity
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.app.BaseAppComponent
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.module.AppModule
import com.jenshen.smartmirror.di.module.DatabaseModule
import com.jenshen.smartmirror.di.module.activity.ActivityBindingModule
import com.jenshen.smartmirror.di.module.firebase.FirebaseModule
import com.jenshen.smartmirror.di.module.interactor.firebase.api.ApiInteractorModule
import com.jenshen.smartmirror.di.module.interactor.firebase.auth.AuthInteractorModule
import com.jenshen.smartmirror.di.module.manager.firebase.database.RealtimeDatabaseModule
import com.jenshen.smartmirror.manager.fabric.FabricManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class,
        DatabaseModule::class,
        FirebaseModule::class,
        RealtimeDatabaseModule::class,
        ApiInteractorModule::class,
        AuthInteractorModule::class,
        ActivityBindingModule::class))
interface AppComponent : BaseAppComponent<SmartMirrorApp> {

    fun userComponentBuilder(): SessionComponent.Builder

    fun provideFabricManager(): FabricManager

    fun provideMultiBuildersForActivities(): Map<Class<out Activity>, ActivityComponentBuilder<*>>
}