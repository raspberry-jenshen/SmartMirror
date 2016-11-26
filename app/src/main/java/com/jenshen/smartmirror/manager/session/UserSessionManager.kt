package com.jenshen.smartmirror.manager.session

import com.google.firebase.auth.FirebaseAuth
import com.jenshen.smartmirror.data.entity.session.Session
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import io.reactivex.Maybe
import javax.inject.Inject

class UserSessionManager @Inject constructor(private var firebaseAuth: FirebaseAuth,
                                             private var preferencesManager: PreferencesManager) : SessionManager {
    override fun getUser(): Maybe<Session> {
        return Maybe.create<Session> {
            val user = preferencesManager.getSession()
            if (user != null) {
                it.onSuccess(user)
            } else {
                it.onComplete()
            }
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}