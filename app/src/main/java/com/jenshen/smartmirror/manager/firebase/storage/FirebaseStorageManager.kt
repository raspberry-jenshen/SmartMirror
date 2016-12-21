package com.jenshen.smartmirror.manager.firebase.storage

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jenshen.smartmirror.data.firebase.FirebaseStorageConstant
import io.reactivex.Single
import javax.inject.Inject


class FirebaseStorageManager @Inject constructor(private val firebaseStorage: FirebaseStorage) : IStorageManager {

    override fun getRootRef(): Single<StorageReference> {
        return Single.fromCallable { firebaseStorage.reference }
    }

    override fun getImagesRef(): Single<StorageReference> {
        return getRootRef()
                .map { it.child(FirebaseStorageConstant.IMAGES) }
    }
}