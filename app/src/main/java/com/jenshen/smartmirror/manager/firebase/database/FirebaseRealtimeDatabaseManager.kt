package com.jenshen.smartmirror.manager.firebase.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jenshen.smartmirror.data.firebase.FirebaseConstant
import io.reactivex.Single
import javax.inject.Inject

class FirebaseRealtimeDatabaseManager : RealtimeDatabaseManager {

    private val fireBaseDatabase: FirebaseDatabase

    @Inject
    constructor(fireBaseDatabase: FirebaseDatabase) {
        this.fireBaseDatabase = fireBaseDatabase
    }

    override fun getTunersRef(): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.TUNERS) }
    }

    override fun getTunerRef(id: String): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.TUNERS) }
                .map { it.child(id) }
    }

    override fun getMirrorsRef(): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.MIRRORS) }
    }

    override fun getMirrorRef(id: String): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.MIRRORS) }
                .map { it.child(id) }
    }
}