package com.jenshen.smartmirror.interactor.firebase.auth

import com.jenshen.smartmirror.manager.auth.AuthManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.model.User
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject


class FirebaseAuthInteractor @Inject constructor(private var authManager: AuthManager,
                                                 private var preferencesManager: PreferencesManager) : AuthInteractor {

    override fun signIn(email: String, password: String): Single<Boolean> {
        return Single.zip(authManager.makeRequestFirebaseUser()
                .doOnSuccess { preferencesManager.sighIn(User(it.uid,it.email), false) }
                .isEmpty,
                authManager.signInWithEmailAndPassword(email, password).toSingle { true },
                BiFunction { isUserEmpty: Boolean, ignored: Boolean -> !isUserEmpty })
    }

    override fun createNewUser(email: String, password: String): Single<Boolean> {
        return Single.zip(authManager.makeRequestFirebaseUser()
                .doOnSuccess { preferencesManager.sighIn(User(it.uid,it.email), false) }
                .isEmpty,
                authManager.createNewUser(email, password).toSingle { true },
                BiFunction { isUserEmpty: Boolean, ignored: Boolean -> !isUserEmpty })
    }
}