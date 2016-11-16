package com.jenshen.smartmirror.manager.db.firebase

import com.jenshen.smartmirror.model.User
import io.reactivex.Single
import javax.inject.Inject


class FirebaseRealtimeDatabaseManager : RealtimeDatabaseManager {

    @Inject
    constructor()

    override fun createOrGetMirror(mac: String): Single<User> {

    }
}
