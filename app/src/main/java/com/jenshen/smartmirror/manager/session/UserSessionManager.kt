package com.jenshen.smartmirror.manager.session

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserSessionManager @Inject constructor(private var firebaseAuth: FirebaseAuth) : SessionManager {

    override fun logout() {
        firebaseAuth.signOut()
    }
}