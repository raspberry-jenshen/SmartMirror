package com.jenshen.smartmirror.manager.fabric

import com.crashlytics.android.Crashlytics
import com.jenshen.smartmirror.data.entity.session.Session
import com.jenshen.smartmirror.data.entity.session.TunerSession
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FabricManager {

    @Inject constructor()

    fun setLogUser(session: Session) {
        Crashlytics.setUserIdentifier(session.key)
        if (session is TunerSession) {
            Crashlytics.setUserEmail(session.email)
        }
    }

    fun releaseLogUser() {
        Crashlytics.setUserIdentifier(null)
        Crashlytics.setUserEmail(null)
    }
}