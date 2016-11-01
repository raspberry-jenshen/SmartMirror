package com.jenshen.smartmirror.app

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.jenshen.smartmirror.BuildConfig
import com.jenshen.smartmirror.di.component.AppComponent
import com.jenshen.smartmirror.di.component.DaggerAppComponent
import com.jenshen.smartmirror.di.component.UserComponent
import com.jenshen.smartmirror.di.module.AppModule
import com.jenshen.smartmirror.util.delegate.lazyValue
import com.squareup.leakcanary.LeakCanary
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import io.realm.RealmConfiguration
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.R

open class Application : Application() {

    companion object {

        @JvmStatic lateinit var appComponent: AppComponent

        @JvmStatic val fabricManager by lazy {
            appComponent.provideFabricManager()
        }

        @JvmStatic var userComponent: UserComponent? by lazyValue {
            val userComponent = appComponent.userComponentBuilder().build()
            fabricManager.setLogUser(userComponent.provideUser())
            return@lazyValue userComponent
        }

        @JvmStatic fun releaseUserComponent() {
            userComponent = null
            fabricManager.releaseLogUser()
        }
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        LeakCanary.install(this)

        Fabric.with(this, Crashlytics.Builder()
                .core(CrashlyticsCore.Builder()
                        .disabled(BuildConfig.DEBUG)
                        .build())
                .build())

        val configuration = RealmConfiguration.Builder()
                .name("smartMirror.realm")
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(configuration)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                //.setDefaultFontPath("fonts/dinpro-medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}