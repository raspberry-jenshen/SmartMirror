package ua.regin.pocket.manager.preference

import io.reactivex.Completable
import io.reactivex.Single
import com.jenshen.smartmirror.model.User

interface PreferencesManager {

    fun login(user: User, token: String)

    fun logout(): Completable

    fun isSessionExist(): Single<Boolean>

    fun getSession(): String?

    fun getUser(): User
}
