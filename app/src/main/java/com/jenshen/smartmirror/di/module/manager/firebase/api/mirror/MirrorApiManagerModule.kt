package com.jenshen.smartmirror.di.module.manager.firebase.api.mirror

import com.jenshen.smartmirror.manager.firebase.api.mirror.MirrorFirebaseApiManager
import com.jenshen.smartmirror.manager.firebase.api.mirror.MirrorApiManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class MirrorApiManagerModule {

    @Singleton
    @Binds
    abstract fun bindMirrorApiManager(authInteractor: MirrorFirebaseApiManager) : MirrorApiManager
}