package com.jenshen.smartmirror.di.module.interactor.firebase.storage

import com.jenshen.smartmirror.di.module.manager.firebase.storage.StorageManagerModule
import com.jenshen.smartmirror.interactor.firebase.storage.FirebaseStorageInteractor
import com.jenshen.smartmirror.interactor.firebase.storage.IStorageInteractor
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = arrayOf(StorageManagerModule::class))
abstract class StorageInteractorModule {

    @Singleton
    @Binds
    abstract fun bindStorageModule(storageInteractor: FirebaseStorageInteractor): IStorageInteractor
}