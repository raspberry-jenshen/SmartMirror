package com.jenshen.smartmirror.app

import com.facebook.stetho.Stetho

class MyDebugSmartMirrorApp : SmartMirrorApp() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        //.enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build())
    }
}