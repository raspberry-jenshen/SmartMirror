package com.jenshen.smartmirror.app;

import com.facebook.stetho.Stetho;
import com.jenshen.smartmirror.app.Application;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

public class MyDebugApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }
}