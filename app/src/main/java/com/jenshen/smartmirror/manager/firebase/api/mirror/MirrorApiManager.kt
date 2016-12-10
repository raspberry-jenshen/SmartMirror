package com.jenshen.smartmirror.manager.firebase.api.mirror

import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.data.firebase.model.mirror.MirrorConfigurationInfo
import io.reactivex.Flowable
import io.reactivex.Single


interface MirrorApiManager {
    fun observeSelectedConfigurationKey(mirrorKey: String): Flowable<String>
    fun getMirrorConfiguration(configurationKey: String): Single<DataSnapshotWithKey<MirrorConfiguration>>
    fun observeMirrorConfigurationInfoForMirror(mirrorKey: String, configurationKey: String): Flowable<MirrorConfigurationInfo>
}