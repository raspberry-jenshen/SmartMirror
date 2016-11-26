package com.jenshen.smartmirror.di.module.manager.firebase.api.tuner

import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.firebase.api.tuner.TunerApiManager
import com.jenshen.smartmirror.manager.firebase.api.tuner.TunerFirebaseApiManager
import dagger.Binds
import dagger.Module

@Module
abstract class TunerApiManagerModule {

    @SessionScope
    @Binds
    abstract fun bindTunerApiManager(authInteractor: TunerFirebaseApiManager) : TunerApiManager
}