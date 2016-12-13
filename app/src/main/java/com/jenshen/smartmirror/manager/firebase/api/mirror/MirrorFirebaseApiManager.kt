package com.jenshen.smartmirror.manager.firebase.api.mirror

import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.data.firebase.model.mirror.MirrorConfigurationInfo
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

    override fun getMirrorConfiguration(configurationKey: String): Single<DataSnapshotWithKey<MirrorConfiguration>> {
        return firebaseDatabase.getMirrorConfigurationRef(configurationKey)
                .doOnSuccess { it.keepSynced(true) }
                .flatMap { it.loadValue() }
                .map { DataSnapshotWithKey(it.key, it.getValue(MirrorConfiguration::class.java)) }
    }

    override fun observeMirrorConfigurationInfoForMirror(mirrorKey: String, configurationKey: String): Flowable<MirrorConfigurationInfo> {
        return firebaseDatabase.getMirrorConfigurationsInfoRef(mirrorKey)
                .map { it.child(configurationKey)}
                .flatMapPublisher { it.observeValue() }
                .filter { it.exists() }
                .map { it.getValue(MirrorConfigurationInfo::class.java) }
    }

}