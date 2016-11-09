package com.jenshen.smartmirror.di.module.manager

import com.jenshen.smartmirror.manager.auth.AuthManager
import com.jenshen.smartmirror.manager.auth.FirebaseAuthManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AuthManagerModule {

    @Singleton
    @Binds
    abstract fun bindAuthManager(authManager: FirebaseAuthManager) : AuthManager
}