package com.jenshen.smartmirror.manager.firebase.auth

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.jenshen.smartmirror.R
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import javax.inject.Inject


class FirebaseAuthManager @Inject constructor(private val context: Context, private val fireBaseAuth: FirebaseAuth) : AuthManager {

    override val isUserExists: Boolean
        get() = fireBaseAuth.currentUser != null

    override fun fetchFirebaseUser(): Observable<FirebaseUser> {
        return Observable.create { source ->
            val authStateListener = FirebaseAuth.AuthStateListener {
                val currentUser = it.currentUser
                if (currentUser != null) source.onNext(currentUser)
            }
            fireBaseAuth.addAuthStateListener(authStateListener)
            source.setCancellable { fireBaseAuth.removeAuthStateListener(authStateListener) }
        }
    }

    override fun getFirebaseUser(): Maybe<FirebaseUser> {
        return Maybe.create {
            if (fireBaseAuth.currentUser != null) {
                it.onSuccess(fireBaseAuth.currentUser)
            } else {
                it.onComplete()
            }
        }
    }

    override fun signInAnonymously(): Completable {
        return Completable.create { emitter ->
            fireBaseAuth.signInAnonymously()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            emitter.onComplete()
                        } else {
                            emitter.onError(it.exception)
                        }
                    }
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

    override fun updateProfile(name: String, uri: Uri?): Completable {
        return getFirebaseUser()
                .switchIfEmpty { throw RuntimeException(context.getString(R.string.error_cant_find_user)) }
                .flatMapCompletable {
                    Completable.create { emitter ->
                        val profileBuilder = UserProfileChangeRequest.Builder()
                        profileBuilder.setDisplayName(name)
                        if (uri != null) {
                            profileBuilder.setPhotoUri(uri)
                        }
                        it.updateProfile(profileBuilder.build())
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

    override fun resetPassword(email: String): Completable {
        return Completable.create { emitter ->
            fireBaseAuth.sendPasswordResetEmail(email)
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