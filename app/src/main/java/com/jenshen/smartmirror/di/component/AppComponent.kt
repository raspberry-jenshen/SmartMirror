package com.jenshen.smartmirror.di.component

import android.app.Activity
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.app.BaseAppComponent
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.module.AppModule
import com.jenshen.smartmirror.di.module.activity.ActivityBindingModule
import com.jenshen.smartmirror.di.module.firebase.FirebaseModule
import com.jenshen.smartmirror.di.module.interactor.AuthInteractorModule
import com.jenshen.smartmirror.di.module.manager.AuthManagerModule
import com.jenshen.smartmirror.manager.fabric.FabricManager
import dagger.Component
import ua.regin.pocket.di.module.DatabaseModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class,
        DatabaseModule::class,
        ActivityBindingModule::class,
        FirebaseModule::class,
        AuthInteractorModule::class))
interface AppComponent : BaseAppComponent<SmartMirrorApp> {

    fun userComponentBuilder(): UserComponent.Builder

    fun provideFabricManager(): FabricManager

    fun provideMultiBuildersForActivities(): Map<Class<out Activity>, ActivityComponentBuilder<*>>
}