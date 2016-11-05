package com.jenshen.smartmirror.manager.fabric

import com.crashlytics.android.Crashlytics
import com.jenshen.smartmirror.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FabricManager {

    @Inject constructor()

    fun setLogUser(user: User) {
        Crashlytics.setUserIdentifier(user.id.toString())
        Crashlytics.setUserEmail(user.email)
    }

    fun releaseLogUser() {
        Crashlytics.setUserIdentifier(null)
        Crashlytics.setUserEmail(null)
    }
}