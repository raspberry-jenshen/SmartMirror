package com.jenshen.smartmirror.manager.firebase.auth

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

interface AuthManager {

    val isUserExists: Boolean
    fun fetchFirebaseUser(): Observable<FirebaseUser>
    fun getFirebaseUser(): Maybe<FirebaseUser>

    fun createNewUser(email: String, password: String): Completable
    fun signInWithEmailAndPassword(email: String, password: String): Completable
    fun updateProfile(name: String, uri: Uri?): Completable
    fun signInAnonymously(): Completable
    fun resetPassword(email: String): Completable
}