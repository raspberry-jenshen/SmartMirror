package com.jenshen.smartmirror.interactor.firebase.auth

import com.jenshen.smartmirror.data.entity.SignInResponse
import io.reactivex.Single


interface AuthInteractor {
    fun signIn(email: String, password: String): Single<Boolean>
    fun createNewUser(email: String, password: String): Single<Boolean>
}