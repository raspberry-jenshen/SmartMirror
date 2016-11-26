package com.jenshen.smartmirror.di.module.interactor.firebase.auth

import com.jenshen.smartmirror.di.module.manager.firebase.auth.AuthManagerModule
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