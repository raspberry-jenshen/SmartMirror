package com.jenshen.smartmirror.di.module.manager

import com.jenshen.smartmirror.di.scope.UserScope
import com.jenshen.smartmirror.manager.auth.AuthManager
import com.jenshen.smartmirror.manager.auth.FirebaseAuthManager
import com.jenshen.smartmirror.manager.db.firebase.FirebaseRealtimeDatabaseManager
import com.jenshen.smartmirror.manager.db.firebase.RealtimeDatabaseManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class FiredataRealtineDatabaseModule {

    @UserScope
    @Binds
    abstract fun bindRealtimeDatabaseManager(authManager: FirebaseRealtimeDatabaseManager) : RealtimeDatabaseManager
}