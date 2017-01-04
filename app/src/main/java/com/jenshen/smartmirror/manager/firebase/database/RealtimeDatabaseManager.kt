package com.jenshen.smartmirror.manager.firebase.database

import com.google.firebase.database.DatabaseReference
import io.reactivex.Single


interface RealtimeDatabaseManager {

    /* mirror */

    fun getMirrorsRef(): Single<DatabaseReference>
    fun getMirrorRef(mirrorKey: String): Single<DatabaseReference>
    fun getIsWaitingForTunerFlagRef(mirrorKey: String): Single<DatabaseReference>
    fun getSelectedConfigurationRef(mirrorKey: String): Single<DatabaseReference>
    fun getMirrorSubscribersRef(mirrorKey: String): Single<DatabaseReference>
    fun getMirrorConfigurationsInfoRef(mirrorKey: String): Single<DatabaseReference>
    fun getMirrorConfigurationInfoRef(configurationKey: String, mirrorKey: String): Single<DatabaseReference>

    /* tuner */

    fun getTunersRef(): Single<DatabaseReference>
    fun getTunerRef(tunerKey: String): Single<DatabaseReference>
    fun getTunerInfoRef(tunerKey: String): Single<DatabaseReference>
    fun getTunerSubscriptionsRef(tunerKey: String): Single<DatabaseReference>
    fun getTunerSubscriptionRef(mirrorKey: String, tunerKey: String): Single<DatabaseReference>

    /* widget */

    fun getWidgetsRef(): Single<DatabaseReference>
    fun getWidgetRef(widgetKey: String): Single<DatabaseReference>

    /* mirror configuration */

    fun getMirrorsConfigurationsRef(): Single<DatabaseReference>
    fun getMirrorConfigurationRef(configurationKey: String): Single<DatabaseReference>
    fun getMirrorConfigurationWidgetsRef(configurationKey: String): Single<DatabaseReference>
    fun getMirrorConfigurationWidgetRef(widgetKey: String, configurationKey: String): Single<DatabaseReference>
    fun getMirrorConfigurationOrientationModeRef(configurationKey: String): Single<DatabaseReference>
    fun getMirrorConfigurationUserInfoKeyRef(configurationKey: String): Single<DatabaseReference>
    fun getMirrorConfigurationIsEnablePrecipitationRef(configurationKey: String): Single<DatabaseReference>
}