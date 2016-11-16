package com.jenshen.smartmirror.manager.db.firebase

import com.jenshen.smartmirror.model.User
import io.reactivex.Single


interface RealtimeDatabaseManager {

    fun createOrGetMirror(mac: String): Single<User>
}
