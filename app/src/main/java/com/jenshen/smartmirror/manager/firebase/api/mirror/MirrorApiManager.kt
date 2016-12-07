package com.jenshen.smartmirror.manager.firebase.api.mirror

import com.jenshen.smartmirror.data.firebase.DataShapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import io.reactivex.Flowable
import io.reactivex.Single


interface MirrorApiManager {
    fun observeSelectedConfigurationKey(mirrorKey: String): Flowable<String>
    fun getMirrorConfiguration(configurationKey: String): Single<DataShapshotWithKey<MirrorConfiguration>>
}