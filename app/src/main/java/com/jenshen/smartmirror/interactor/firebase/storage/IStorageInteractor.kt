package com.jenshen.smartmirror.interactor.firebase.storage

import android.net.Uri
import io.reactivex.Single


interface IStorageInteractor {
    fun uploadImage(key: String, uri: Uri): Single<Uri>
}