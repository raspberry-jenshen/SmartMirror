package com.jenshen.smartmirror.di.component

import com.jenshen.smartmirror.di.component.activity.StartComponent
import com.jenshen.smartmirror.di.module.AppModule
import com.jenshen.smartmirror.manager.fabric.FabricManager
import dagger.Component
import ua.regin.pocket.di.module.DatabaseModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DatabaseModule::class))
interface AppComponent {

    fun userComponentBuilder(): UserComponent.Builder

    fun provideFabricManager(): FabricManager

    fun plusStartComponent(): StartComponent
}