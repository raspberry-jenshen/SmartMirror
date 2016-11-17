package com.jenshen.smartmirror.di.module.interactor.firebase.api.mirror

import com.jenshen.smartmirror.di.module.manager.firebase.api.mirror.MirrorApiManagerModule
import com.jenshen.smartmirror.interactor.firebase.api.mirror.MirrorApiInteractor
import com.jenshen.smartmirror.interactor.firebase.api.mirror.MirrorFirebaseApiInteractor
import dagger.Binds
import dagger.Module

@Module(includes = arrayOf(MirrorApiManagerModule::class))
abstract class MirrorApiInteractorModule {

    @Binds
    abstract fun bindMirrorApiInteractor(apiInteractorModule: MirrorFirebaseApiInteractor): MirrorApiInteractor
}