package com.jenshen.smartmirror.di.module

import com.jenshen.smartmirror.manager.db.RealmDatabase
import dagger.Binds
import dagger.Module
import ua.regin.pocket.manager.db.Database
import javax.inject.Singleton

@Singleton
@Module
abstract class DatabaseModule {

    @Binds
    abstract fun provideDatabase(realmDatabase: RealmDatabase): Database

}