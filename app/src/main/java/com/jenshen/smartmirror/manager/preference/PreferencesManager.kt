package com.jenshen.smartmirror.manager.preference

import com.jenshen.smartmirror.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface PreferencesManager {

    fun sighIn(user: User, isMirror: Boolean)

    fun logout(): Completable

    fun getUser(): User?

    fun isMirror(): Boolean
}
