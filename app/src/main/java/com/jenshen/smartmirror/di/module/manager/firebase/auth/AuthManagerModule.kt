package com.jenshen.smartmirror.di.module.manager.firebase.auth

import com.jenshen.smartmirror.manager.firebase.auth.AuthManager
import com.jenshen.smartmirror.manager.firebase.auth.FirebaseAuthManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AuthManagerModule {

    @Singleton
    @Binds
    abstract fun bindAuthManager(authManager: FirebaseAuthManager) : AuthManager
}