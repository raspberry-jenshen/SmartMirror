package com.jenshen.smartmirror.manager.firebase.api.mirror

import com.jenshen.smartmirror.data.firebase.DataShapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.manager.firebase.database.RealtimeDatabaseManager
import com.jenshen.smartmirror.util.reactive.firebase.loadValue
import com.jenshen.smartmirror.util.reactive.firebase.observeValue
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class MirrorFirebaseApiManager @Inject constructor(private var firebaseDatabase: RealtimeDatabaseManager) : MirrorApiManager {

    override fun observeSelectedConfigurationKey(mirrorKey: String): Flowable<String> {
        return firebaseDatabase.getSelectedConfigurationRef(mirrorKey)
                .flatMapPublisher { it.observeValue() }
                .filter { it.exists() }
                .map { it.getValue(String::class.java) }
    }

    override fun getMirrorConfiguration(configurationKey: String): Single<DataShapshotWithKey<MirrorConfiguration>> {
        return firebaseDatabase.getMirrorConfigurationRef(configurationKey)
                .flatMap { it.loadValue() }
                .map { DataShapshotWithKey(it.key, it.getValue(MirrorConfiguration::class.java)) }
    }
}