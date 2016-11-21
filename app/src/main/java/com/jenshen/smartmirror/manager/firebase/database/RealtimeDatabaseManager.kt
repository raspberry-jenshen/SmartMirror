package com.jenshen.smartmirror.manager.firebase.database

import com.google.firebase.database.DatabaseReference
import io.reactivex.Single


interface RealtimeDatabaseManager {

    fun getTunersRef(): Single<DatabaseReference>
    fun getTunerRef(id: String): Single<DatabaseReference>

    fun getMirrorsRef(): Single<DatabaseReference>
    fun getMirrorRef(id: String): Single<DatabaseReference>

    fun getIsWaitingForTunerFlagRef(id: String): Single<DatabaseReference>

    fun getMirrorSubscribersRef(id: String): Single<DatabaseReference>
    fun getTunerSubscriptionsRef(id: String): Single<DatabaseReference>

}