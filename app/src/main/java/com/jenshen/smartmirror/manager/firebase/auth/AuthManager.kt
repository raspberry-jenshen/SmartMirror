package com.jenshen.smartmirror.manager.firebase.auth

import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Observable

interface AuthManager {

    val isUserExists: Boolean

    fun fetchFirebaseUser(): Observable<FirebaseUser>

    fun createNewUser(email: String, password: String): Completable
    fun signInWithEmailAndPassword(email: String, password: String): Completable
    fun signInAnonymously(): Completable
}