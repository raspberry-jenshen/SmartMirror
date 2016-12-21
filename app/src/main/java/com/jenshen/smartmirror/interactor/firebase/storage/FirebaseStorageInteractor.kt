package com.jenshen.smartmirror.interactor.firebase.storage

import android.net.Uri
import com.jenshen.smartmirror.manager.firebase.storage.FirebaseStorageManager
import com.jenshen.smartmirror.util.reactive.firebase.uploadFile
import io.reactivex.Single
import javax.inject.Inject


class FirebaseStorageInteractor @Inject constructor(private val firebaseStorageManager: FirebaseStorageManager) : IStorageInteractor {

    override fun uploadImage(key: String, uri: Uri) : Single<Uri> {
        return firebaseStorageManager.getImagesRef()
                .map { it.child(key) }
                .flatMap { it.uploadFile(uri)}
    }
}