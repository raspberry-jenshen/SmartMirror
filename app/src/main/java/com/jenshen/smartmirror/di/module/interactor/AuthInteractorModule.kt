package com.jenshen.smartmirror.di.module.interactor

import com.jenshen.smartmirror.di.module.manager.AuthManagerModule
import com.jenshen.smartmirror.interactor.firebase.auth.AuthInteractor
import com.jenshen.smartmirror.interactor.firebase.auth.FirebaseAuthInteractor
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = arrayOf(AuthManagerModule::class))
abstract class AuthInteractorModule {

    @Singleton
    @Binds
    abstract fun bindAuthInteractor(authInteractor: FirebaseAuthInteractor) : AuthInteractor
}