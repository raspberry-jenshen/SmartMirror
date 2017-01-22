package com.jenshen.smartmirror.manager.firebase.api.mirror

import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.data.firebase.model.configuration.OrientationMode
import com.jenshen.smartmirror.data.firebase.model.mirror.MirrorConfigurationInfo
import com.jenshen.smartmirror.data.firebase.model.tuner.TunerInfo
import com.jenshen.smartmirror.util.Optional
import io.reactivex.Flowable
import io.reactivex.Single


interface MirrorApiManager {
    fun observeSelectedConfigurationKey(mirrorKey: String): Flowable<String>
    fun getMirrorConfiguration(configurationKey: String): Single<DataSnapshotWithKey<MirrorConfiguration>>
    fun observeMirrorConfigurationInfoForMirror(mirrorKey: String, configurationKey: String): Flowable<MirrorConfigurationInfo>
    fun observeUserInfoOnMirror(configurationKey: String): Flowable<Optional<TunerInfo>>
    fun observeIsEnablePrecipitation(configurationKey: String): Flowable<Boolean>
    fun observeScreenOrientation(configurationKey: String): Flowable<OrientationMode>
}