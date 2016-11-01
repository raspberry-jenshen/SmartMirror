package com.jenshen.smartmirror.app

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.jenshen.smartmirror.BuildConfig
import com.squareup.leakcanary.LeakCanary
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import io.realm.RealmConfiguration

open class Application : Application() {

    /*companion object {
        private val BASE_URL: String = "http://52.212.146.33/api/"

        @JvmStatic lateinit var appComponent: AppComponent

        @JvmStatic var userComponent: UserComponent? by lazyValue {
            val userComponent = appComponent.userComponentBuilder().build()
            fabricManager.setLogUser(userComponent.provideUser())
            return@lazyValue userComponent
        }

        @JvmStatic fun releaseUserComponent() {
            userComponent = null
            fabricManager.releaseLogUser()
        }

        @JvmStatic val fabricManager by lazy {
            appComponent.provideFabricManager()
        }
    }*/

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
                .name("parlor.realm")
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(configuration)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

       /* CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/dinpro-medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())

        RxFcm.Notifications.init(this, FcmReceiverData(), FcmReceiverBackground())



        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule(BASE_URL))
                .build()*/
    }
}