package com.jenshen.smartmirror.di.module.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Singleton
    @Provides
    fun provideAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun provideDatabase(): FirebaseDatabase {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase.setPersistenceEnabled(true)
        return firebaseDatabase
    }
}