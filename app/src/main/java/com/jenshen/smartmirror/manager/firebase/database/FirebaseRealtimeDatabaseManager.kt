package com.jenshen.smartmirror.manager.firebase.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jenshen.smartmirror.data.firebase.FirebaseConstant
import io.reactivex.Single
import javax.inject.Inject

class FirebaseRealtimeDatabaseManager @Inject constructor(private val fireBaseDatabase: FirebaseDatabase) : RealtimeDatabaseManager {

    /* mirror */

    override fun getMirrorsRef(): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.MIRRORS) }
    }

    override fun getMirrorRef(mirrorId: String): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.MIRRORS) }
                .map { it.child(mirrorId) }
    }

    override fun getIsWaitingForTunerFlagRef(id: String): Single<DatabaseReference> {
        return getMirrorRef(id)
                .map { it.child(FirebaseConstant.Mirror.IS_WAITING_FOR_TUNER)}
    }

    override fun getSelectedConfigurationRef(id: String): Single<DatabaseReference> {
        return getMirrorRef(id)
                .map { it.child(FirebaseConstant.Mirror.SELECTED_CONFIGURATION_ID)}
    }

    override fun getMirrorSubscribersRef(mirrorId: String): Single<DatabaseReference> {
        return getMirrorRef(mirrorId)
                .map { it.child(FirebaseConstant.Mirror.SUBSCRIBERS)}
    }

    override fun getMirrorConfigurationsInfoRef(mirrorId: String): Single<DatabaseReference> {
        return getMirrorRef(mirrorId)
                .map { it.child(FirebaseConstant.Mirror.CONFIGURATIONS)}
    }

    /* tuner */

    override fun getTunersRef(): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.TUNERS) }
    }

    override fun getTunerRef(tunerId: String): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.TUNERS) }
                .map { it.child(tunerId) }
    }
    override fun getTunerSubscriptionsRef(tunerId: String): Single<DatabaseReference> {
        return getTunerRef(tunerId)
                .map { it.child(FirebaseConstant.Tuner.SUBSCRIPTIONS)}
    }

    /* widget */

    override fun getWidgetsRef(): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.WIDGETS) }
    }
}