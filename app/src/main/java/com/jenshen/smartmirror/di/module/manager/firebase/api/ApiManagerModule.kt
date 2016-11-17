package com.jenshen.smartmirror.di.module.manager.firebase.api

import com.jenshen.smartmirror.manager.firebase.api.ApiManager
import com.jenshen.smartmirror.manager.firebase.api.FirebaseApiManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ApiManagerModule {

    @Singleton
    @Binds
    abstract fun bindApiManager(apiManager: FirebaseApiManager): ApiManager
}