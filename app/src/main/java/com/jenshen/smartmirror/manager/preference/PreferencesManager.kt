package com.jenshen.smartmirror.manager.preference

import com.jenshen.smartmirror.data.entity.session.Session
import io.reactivex.Completable
import io.reactivex.Single

interface PreferencesManager {

    fun sighIn(session: Session, isMirror: Boolean)

    fun logout(): Completable

    fun getSession(): Session?

    fun isMirror(): Boolean
}
