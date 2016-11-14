package ua.regin.pocket.di.module

import dagger.Binds
import dagger.Module
import ua.regin.pocket.manager.db.Database
import com.jenshen.smartmirror.manager.db.RealmDatabase
import javax.inject.Singleton

@Singleton
@Module
abstract class DatabaseModule {

    @Binds
    abstract fun provideDatabase(realmDatabase: RealmDatabase): Database

}