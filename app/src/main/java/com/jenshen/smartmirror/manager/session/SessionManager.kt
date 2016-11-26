package com.jenshen.smartmirror.manager.session

import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.data.entity.session.Session
import io.reactivex.CompletableSource
import io.reactivex.Maybe

interface SessionManager {

    fun logout()

    fun getUser(): Maybe<Session>
}