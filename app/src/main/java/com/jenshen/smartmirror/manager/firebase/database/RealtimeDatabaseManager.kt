package com.jenshen.smartmirror.manager.firebase.database

import com.google.firebase.database.DatabaseReference
import io.reactivex.Single


interface RealtimeDatabaseManager {
    fun getMirrorsRef(): Single<DatabaseReference>
    fun getMirrorRef(id: String): Single<DatabaseReference>
}