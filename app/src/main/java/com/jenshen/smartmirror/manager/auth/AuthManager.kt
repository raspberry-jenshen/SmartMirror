package com.jenshen.smartmirror.manager.auth

import android.support.annotation.StringDef
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Single

interface AuthManager {

    /* inner types */

    @StringDef(UNCONNECTED, CONNECTING, CONNECTION_SUCCESS, CONNECTION_FAILED)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ConnectionState

    companion object {
        const val UNCONNECTED = "UNCONNECTED"
        const val CONNECTION_SUCCESS = "CONNECTION_SUCCESS"
        const val CONNECTION_FAILED = "CONNECTION_FAILED"
        const val CONNECTING = "CONNECTING"
    }

    fun createNewUser(email: String, password: String): Completable
    fun signInWithEmailAndPassword(email: String, password: String): Completable
    fun fetchFirebaseUser(): Single<FirebaseUser>
}