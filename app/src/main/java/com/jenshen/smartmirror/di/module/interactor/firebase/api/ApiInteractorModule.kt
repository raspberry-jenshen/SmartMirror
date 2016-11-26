package com.jenshen.smartmirror.di.module.interactor.firebase.api

import com.jenshen.smartmirror.di.module.manager.firebase.api.ApiManagerModule
import com.jenshen.smartmirror.interactor.firebase.api.ApiInteractor
import com.jenshen.smartmirror.interactor.firebase.api.FirebaseApiInteractor
import dagger.Binds
import dagger.Module

@Module(includes = arrayOf(ApiManagerModule::class))
abstract class ApiInteractorModule {

    @Binds
    abstract fun bindApiInteractor(apiInteractorModule: FirebaseApiInteractor): ApiInteractor
}