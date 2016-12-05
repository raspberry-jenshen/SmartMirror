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

    override fun getMirrorRef(mirrorKey: String): Single<DatabaseReference> {
        return getMirrorsRef()
                .map { it.child(mirrorKey) }
    }

    override fun getIsWaitingForTunerFlagRef(mirrorKey: String): Single<DatabaseReference> {
        return getMirrorRef(mirrorKey)
                .map { it.child(FirebaseConstant.Mirror.IS_WAITING_FOR_TUNER) }
    }

    override fun getSelectedConfigurationRef(mirrorKey: String): Single<DatabaseReference> {
        return getMirrorRef(mirrorKey)
                .map { it.child(FirebaseConstant.Mirror.SELECTED_CONFIGURATION_ID) }
    }

    override fun getMirrorSubscribersRef(mirrorKey: String): Single<DatabaseReference> {
        return getMirrorRef(mirrorKey)
                .map { it.child(FirebaseConstant.Mirror.SUBSCRIBERS) }
    }

    override fun getMirrorConfigurationsInfoRef(mirrorKey: String): Single<DatabaseReference> {
        return getMirrorRef(mirrorKey)
                .map { it.child(FirebaseConstant.Mirror.CONFIGURATIONS) }
    }

    /* tuner */

    override fun getTunersRef(): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.TUNERS) }
    }

    override fun getTunerRef(tunerKey: String): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.TUNERS) }
                .map { it.child(tunerKey) }
    }

    override fun getTunerSubscriptionsRef(tunerKey: String): Single<DatabaseReference> {
        return getTunerRef(tunerKey)
                .map { it.child(FirebaseConstant.Tuner.SUBSCRIPTIONS) }
    }

    /* widget */

    override fun getWidgetsRef(): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.WIDGETS) }
    }

    /* mirror configuration */

    override fun getMirrorsConfigurationsRef(): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseConstant.MIRROR_CONFIGURATIONS) }
    }

    override fun getMirrorConfigurationRef(configurationKey: String): Single<DatabaseReference> {
        return getMirrorsConfigurationsRef()
                .map { it.child(configurationKey) }
    }

    override fun getMirrorConfigurationWidgetsRef(configurationKey: String): Single<DatabaseReference> {
        return getMirrorConfigurationRef(configurationKey)
                .map { it.child(FirebaseConstant.MirrorConfiguration.WIDGETS) }
    }

    override fun getMirrorConfigurationWidgetRef(widgetKey: String, configurationKey: String): Single<DatabaseReference> {
        return getMirrorConfigurationWidgetsRef(configurationKey)
                .map { it.child(widgetKey) }
    }
}