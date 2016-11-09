package com.jenshen.smartmirror.manager.preference

import com.jenshen.smartmirror.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface PreferencesManager {

    fun login(user: User, token: String)

    fun logout(): Completable

    fun isSessionExist(): Single<Boolean>

    fun getSession(): String?

    fun getUser(): User

    fun isMirror(): Boolean
}
