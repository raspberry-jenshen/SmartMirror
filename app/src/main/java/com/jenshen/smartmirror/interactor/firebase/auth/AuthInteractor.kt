package com.jenshen.smartmirror.interactor.firebase.auth

import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Observable


interface AuthInteractor {
    fun fetchAuth(): Observable<FirebaseUser>

    fun createNewUser(email: String, password: String): Completable
    fun signInMirrorTuner(email: String, password: String): Completable
    fun signInMirror(): Completable
}