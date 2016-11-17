package com.jenshen.smartmirror.di.module.manager.firebase.database

import com.jenshen.smartmirror.manager.firebase.database.FirebaseRealtimeDatabaseManager
import com.jenshen.smartmirror.manager.firebase.database.RealtimeDatabaseManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RealtimeDatabaseModule {

    @Singleton
    @Binds
    abstract fun bindRealtimeDatabaseManager(realtimeDatabaseManager: FirebaseRealtimeDatabaseManager) : RealtimeDatabaseManager
}