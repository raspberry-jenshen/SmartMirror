package com.jenshen.smartmirror.interactor.firebase.auth

import com.google.firebase.auth.FirebaseUser
import com.jenshen.smartmirror.manager.auth.AuthManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject


class FirebaseAuthInteractor @Inject constructor(private var authManager: AuthManager,
                                                 private var preferencesManager: PreferencesManager) : AuthInteractor {

    override fun fetchAuth(): Observable<FirebaseUser> {
        return authManager.fetchFirebaseUser()
                .doOnNext { preferencesManager.sighIn(User(it.uid, it.email), it.isAnonymous) }
    }

    override fun signInMirrorTuner(email: String, password: String): Completable {
        return authManager.signInWithEmailAndPassword(email, password)
    }

    override fun signInMirror(): Completable {
        return authManager.signInAnonymously()
    }

    override fun createNewUser(email: String, password: String): Completable {
        return authManager.createNewUser(email, password)
    }
}