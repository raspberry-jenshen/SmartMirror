package com.jenshen.smartmirror.manager.firebase.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import io.reactivex.Single
import javax.inject.Inject

class FirebaseRealtimeDatabaseManager @Inject constructor(private val fireBaseDatabase: FirebaseDatabase) : RealtimeDatabaseManager {

    /* mirror */

    override fun getMirrorsRef(): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseRealTimeDatabaseConstant.MIRRORS) }
    }

    override fun getMirrorRef(mirrorKey: String): Single<DatabaseReference> {
        return getMirrorsRef()
                .map { it.child(mirrorKey) }
    }

    override fun getIsWaitingForTunerFlagRef(mirrorKey: String): Single<DatabaseReference> {
        return getMirrorRef(mirrorKey)
                .map { it.child(FirebaseRealTimeDatabaseConstant.Mirror.IS_WAITING_FOR_TUNER) }
    }

    override fun getSelectedConfigurationRef(mirrorKey: String): Single<DatabaseReference> {
        return getMirrorRef(mirrorKey)
                .map { it.child(FirebaseRealTimeDatabaseConstant.Mirror.SELECTED_CONFIGURATION_ID) }
    }

    override fun getMirrorSubscribersRef(mirrorKey: String): Single<DatabaseReference> {
        return getMirrorRef(mirrorKey)
                .map { it.child(FirebaseRealTimeDatabaseConstant.Mirror.SUBSCRIBERS) }
    }

    override fun getMirrorConfigurationsInfoRef(mirrorKey: String): Single<DatabaseReference> {
        return getMirrorRef(mirrorKey)
                .map { it.child(FirebaseRealTimeDatabaseConstant.Mirror.CONFIGURATIONS) }
    }

    override fun getMirrorConfigurationInfoRef(configurationKey: String, mirrorKey: String): Single<DatabaseReference> {
        return getMirrorConfigurationsInfoRef(mirrorKey)
                .map { it.child(configurationKey) }
    }

    /* tuner */

    override fun getTunersRef(): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseRealTimeDatabaseConstant.TUNERS) }
    }

    override fun getTunerRef(tunerKey: String): Single<DatabaseReference> {
        return getTunersRef()
                .map { it.child(tunerKey) }
    }

    override fun getTunerInfoRef(tunerKey: String): Single<DatabaseReference> {
        return getTunerRef(tunerKey)
                .map { it.child(FirebaseRealTimeDatabaseConstant.Tuner.TUNER_INFO) }
    }

    override fun getTunerSubscriptionsRef(tunerKey: String): Single<DatabaseReference> {
        return getTunerRef(tunerKey)
                .map { it.child(FirebaseRealTimeDatabaseConstant.Tuner.SUBSCRIPTIONS) }
    }

    override fun getTunerSubscriptionRef(mirrorKey: String, tunerKey: String): Single<DatabaseReference> {
        return getTunerSubscriptionsRef(tunerKey)
                .map { it.child(mirrorKey) }
    }

    /* widget */

    override fun getWidgetsRef(): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseRealTimeDatabaseConstant.WIDGETS) }
    }

    override fun getWidgetRef(widgetKey: String): Single<DatabaseReference> {
        return getWidgetsRef()
                .map { it.child(widgetKey) }
    }

    /* mirror configuration */

    override fun getMirrorsConfigurationsRef(): Single<DatabaseReference> {
        return Single.fromCallable { fireBaseDatabase.reference }
                .map { it.child(FirebaseRealTimeDatabaseConstant.MIRROR_CONFIGURATIONS) }
    }

    override fun getMirrorConfigurationRef(configurationKey: String): Single<DatabaseReference> {
        return getMirrorsConfigurationsRef()
                .map { it.child(configurationKey) }
    }

    override fun getMirrorConfigurationWidgetsRef(configurationKey: String): Single<DatabaseReference> {
        return getMirrorConfigurationRef(configurationKey)
                .map { it.child(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.WIDGETS) }
    }

    override fun getMirrorConfigurationWidgetRef(widgetKey: String, configurationKey: String): Single<DatabaseReference> {
        return getMirrorConfigurationWidgetsRef(configurationKey)
                .map { it.child(widgetKey) }
    }

    override fun getMirrorConfigurationUserInfoKeyRef(configurationKey: String): Single<DatabaseReference> {
        return getMirrorConfigurationRef(configurationKey)
                .map { it.child(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.USER_INFO_KEY) }
    }

    override fun getMirrorConfigurationIsEnablePrecipitationRef(configurationKey: String): Single<DatabaseReference> {
        return getMirrorConfigurationRef(configurationKey)
                .map { it.child(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.IS_ENABLE_PRECIPITATION) }
    }
}