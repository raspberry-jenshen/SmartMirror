package com.jenshen.smartmirror.di.module.interactor.firebase.api.tuner

import com.jenshen.smartmirror.di.module.manager.firebase.api.tuner.TunerApiManagerModule
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerFirebaseApiInteractor
import dagger.Binds
import dagger.Module

@Module(includes = arrayOf(TunerApiManagerModule::class))
abstract class TunerApiInteractorModule {

    @Binds
    abstract fun bindTunerApiInteractor(apiInteractorModule: TunerFirebaseApiInteractor): TunerApiInteractor
}