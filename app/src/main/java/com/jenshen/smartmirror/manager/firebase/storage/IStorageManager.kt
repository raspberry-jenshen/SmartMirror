package com.jenshen.smartmirror.manager.firebase.storage

import com.google.firebase.storage.StorageReference
import io.reactivex.Single


interface IStorageManager {
    fun getRootRef(): Single<StorageReference>
    fun getImagesRef(): Single<StorageReference>
}