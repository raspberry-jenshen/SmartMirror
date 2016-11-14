package com.jenshen.smartmirror.manager.session

import com.google.firebase.auth.FirebaseAuth
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.manager.session.SessionManager
import com.jenshen.smartmirror.model.User
import io.reactivex.Maybe
import javax.inject.Inject

class UserSessionManager @Inject constructor(private var firebaseAuth: FirebaseAuth,
                                             private var preferencesManager: PreferencesManager) : SessionManager {
    override fun getUser(): Maybe<User> {
        return Maybe.create<User> {
            val user = preferencesManager.getUser()
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