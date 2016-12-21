package com.jenshen.smartmirror.di.module.manager.firebase.storage

import com.jenshen.smartmirror.manager.firebase.storage.FirebaseStorageManager
import com.jenshen.smartmirror.manager.firebase.storage.IStorageManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class StorageManagerModule {

    @Singleton
    @Binds
    abstract fun bindStorageManager(storageManager: FirebaseStorageManager) : IStorageManager
}