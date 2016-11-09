package com.jenshen.smartmirror.manager.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class FirebaseAuthManager : AuthManager {

    private val fireBaseAuth: FirebaseAuth
    private val authState: BehaviorSubject<String>
    private val isAuthenticated: Boolean
        get() = fireBaseAuth.currentUser != null
    private var connectionState: String

    init {
        this.connectionState = AuthManager.UNCONNECTED
        this.authState = BehaviorSubject.createDefault(connectionState)
    }

    @Inject constructor(fireBaseAuth: FirebaseAuth) {
        this.fireBaseAuth = fireBaseAuth
    }
/*
    override fun auth(): Completable {
        setConnectionState(CONNECTING)
        return Completable.create {
            fireBaseAuth.s
        }
    }*/

    fun fetchConnectionState(): Observable<String> {
        return authState
    }

    @AuthManager.ConnectionState
    fun getConnectionState(): String {
        if (connectionState == AuthManager.CONNECTION_SUCCESS && !isAuthenticated) {
            connectionState = AuthManager.UNCONNECTED
        }
        return connectionState
    }

    protected fun setConnectionState(@AuthManager.ConnectionState connectionState: String) {
        this.connectionState = connectionState
        authState.onNext(connectionState)
    }

    override fun fetchFirebaseUser(): Single<FirebaseUser> {
        return Single.create { source ->
            val authStateListener = FirebaseAuth.AuthStateListener {
                val currentUser = it.currentUser
                if (currentUser != null) {
                    source.onSuccess(currentUser)
                } else {
                    source.onError(RuntimeException("User can't be null"))
                }
            }
            fireBaseAuth.addAuthStateListener(authStateListener)
            source.setCancellable { fireBaseAuth.removeAuthStateListener(authStateListener) }
        }
    }

    override fun signInWithEmailAndPassword(email: String, password: String): Completable {
        return Completable.create { emitter ->
            fireBaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            emitter.onComplete()
                        } else {
                            emitter.onError(it.exception)
                        }
                    }
        }
    }

    override fun createNewUser(email: String, password: String): Completable {
        return Completable.create { emitter ->
            fireBaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            emitter.onComplete()
                        } else {
                            emitter.onError(it.exception)
                        }
                    }
        }
    }
}