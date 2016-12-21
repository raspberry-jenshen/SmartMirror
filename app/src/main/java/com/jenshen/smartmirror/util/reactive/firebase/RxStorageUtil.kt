package com.jenshen.smartmirror.util.reactive.firebase

import android.net.Uri
import com.google.firebase.storage.StorageReference
import io.reactivex.Single


fun StorageReference.uploadFile(uri: Uri): Single<Uri> {
    return Single.create { source ->
        val uploadTask = this.putFile(uri)
        uploadTask.addOnFailureListener { source.onError(it) }
        uploadTask.addOnSuccessListener { source.onSuccess(it.downloadUrl) }
    }
}