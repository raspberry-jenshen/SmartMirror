package com.jenshen.smartmirror.manager.firebase.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jenshen.smartmirror.data.firebase.FirebaseConstant
import io.reactivex.Single
import javax.inject.Inject

class FirebaseRealtimeDatabaseManager @Inject constructor(private val fireBaseDatabase: FirebaseDatabase) : RealtimeDatabaseManager {

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

    override fun getIsWaitingForTunerFlagRef(id: String): Single<DatabaseReference> {
        return getMirrorRef(id)
                .map { it.child(FirebaseConstant.Mirror.IS_WAITING_FOR_TUNER)}
    }

    override fun getMirrorSubscribersRef(id: String): Single<DatabaseReference> {
        return getMirrorRef(id)
                .map { it.child(FirebaseConstant.Mirror.SUBSCRIBERS)}
    }

    override fun getTunerSubscriptionsRef(id: String): Single<DatabaseReference> {
        return getTunerRef(id)
                .map { it.child(FirebaseConstant.Tuner.SUBSCRIPTIONS)}
    }
}