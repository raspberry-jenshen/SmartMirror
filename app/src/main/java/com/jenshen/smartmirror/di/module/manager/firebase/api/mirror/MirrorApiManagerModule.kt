package com.jenshen.smartmirror.di.module.manager.firebase.api.mirror

import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.firebase.api.mirror.MirrorApiManager
import com.jenshen.smartmirror.manager.firebase.api.mirror.MirrorFirebaseApiManager
import dagger.Binds
import dagger.Module

@Module
abstract class MirrorApiManagerModule {

    @SessionScope
    @Binds
    abstract fun bindMirrorApiManager(authInteractor: MirrorFirebaseApiManager) : MirrorApiManager
}