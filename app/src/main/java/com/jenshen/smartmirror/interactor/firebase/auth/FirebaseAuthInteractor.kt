package com.jenshen.smartmirror.interactor.firebase.auth

import com.google.firebase.auth.FirebaseUser
import com.jenshen.smartmirror.data.entity.SignInResponse
import com.jenshen.smartmirror.manager.auth.AuthManager
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject


class FirebaseAuthInteractor : AuthInteractor {

    private var authManager: AuthManager

    @Inject
    constructor(authManager: AuthManager) {
        this.authManager = authManager
    }

    override fun signIn(email: String, password: String): Single<SignInResponse> {
        return Single.zip(authManager.createNewUser(email, password).toSingle { 0 }, authManager.fetchFirebaseUser(),
                BiFunction { t1: Int, firebaseUser: FirebaseUser -> SignInResponse(firebaseUser) })
    }
}