package com.jenshen.smartmirror.interactor.firebase.auth

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Observable


interface AuthInteractor {
    fun fetchAuth(): Observable<FirebaseUser>

    fun createNewTuner(email: String, password: String): Completable
    fun signInTuner(email: String, password: String): Completable
    fun signInMirror(): Completable
    fun editUserInfo(name: String, uri: Uri): Completable
}