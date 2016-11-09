package com.jenshen.smartmirror.di.module.interactor

import com.jenshen.smartmirror.interactor.firebase.auth.AuthInteractor
import com.jenshen.smartmirror.interactor.firebase.auth.FirebaseAuthInteractor
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AuthInteractorModule {

    @Singleton
    @Binds
    abstract fun bindAuthInteractor(authInteractor: FirebaseAuthInteractor) : AuthInteractor
}