package com.jenshen.smartmirror.manager.firebase.database

import com.google.firebase.database.DatabaseReference
import io.reactivex.Single


interface RealtimeDatabaseManager {

    /* mirror */

    fun getMirrorsRef(): Single<DatabaseReference>
    fun getMirrorRef(mirrorId: String): Single<DatabaseReference>
    fun getIsWaitingForTunerFlagRef(id: String): Single<DatabaseReference>
    fun getSelectedConfigurationRef(id: String): Single<DatabaseReference>
    fun getMirrorSubscribersRef(mirrorId: String): Single<DatabaseReference>
    fun getMirrorConfigurationsInfoRef(mirrorId: String): Single<DatabaseReference>

    /* tuner */

    fun getTunersRef(): Single<DatabaseReference>
    fun getTunerRef(tunerId: String): Single<DatabaseReference>
    fun getTunerSubscriptionsRef(tunerId: String): Single<DatabaseReference>

    /* widget */

    fun getWidgetsRef(): Single<DatabaseReference>
}