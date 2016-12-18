package com.jenshen.smartmirror.di.component

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.jenshen.compat.base.component.activity.ActivityComponentBuilder
import com.jenshen.compat.base.component.app.BaseAppComponent
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.component.activity.start.service.StartMirrorServiceComponent
import com.jenshen.smartmirror.di.module.AppModule
import com.jenshen.smartmirror.di.module.activity.ActivityBindingModule
import com.jenshen.smartmirror.di.module.activity.start.service.StartMirrorServiceModule
import com.jenshen.smartmirror.di.module.firebase.FirebaseModule
import com.jenshen.smartmirror.di.module.interactor.firebase.api.ApiInteractorModule
import com.jenshen.smartmirror.di.module.interactor.firebase.auth.AuthInteractorModule
import com.jenshen.smartmirror.di.module.manager.PreferenceModule
import com.jenshen.smartmirror.di.module.manager.firebase.database.RealtimeDatabaseModule
import com.jenshen.smartmirror.di.module.manager.location.LocationModule
import com.jenshen.smartmirror.interactor.firebase.api.ApiInteractor
import com.jenshen.smartmirror.interactor.firebase.auth.AuthInteractor
import com.jenshen.smartmirror.manager.fabric.FabricManager
import com.jenshen.smartmirror.manager.firebase.api.ApiManager
import com.jenshen.smartmirror.manager.firebase.database.RealtimeDatabaseManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import dagger.Component
import dagger.multibindings.Multibinds
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class,
        PreferenceModule::class,
        FirebaseModule::class,
        RealtimeDatabaseModule::class,
        ApiInteractorModule::class,
        AuthInteractorModule::class,
        ActivityBindingModule::class))
interface AppComponent : BaseAppComponent<SmartMirrorApp> {

    fun userComponentBuilder(): SessionComponent.Builder

    fun provideFabricManager(): FabricManager

    fun providePreferencesManager(): PreferencesManager

    fun provideMultiBuildersForActivities(): Map<Class<out Activity>, ActivityComponentBuilder<*>>

    fun plusMirrorService(mirrorServiceModule: StartMirrorServiceModule) : StartMirrorServiceComponent
}