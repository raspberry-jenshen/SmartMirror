package com.jenshen.smartmirror.interactor.firebase.auth

import com.google.firebase.auth.FirebaseUser
import com.jenshen.smartmirror.data.entity.SignInResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


interface AuthInteractor {
    fun signIn(email: String, password: String): Completable
    fun createNewUser(email: String, password: String): Completable
    open fun fetchAuth(): Observable<FirebaseUser>
}