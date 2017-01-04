package com.jenshen.smartmirror.interactor.firebase.api.mirror

import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.data.firebase.model.configuration.OrientationMode
import com.jenshen.smartmirror.data.firebase.model.tuner.TunerInfo
import com.jenshen.smartmirror.util.Optional
import io.reactivex.Flowable

interface MirrorApiInteractor {
    fun fetchIsNeedToShowQrCode(): Flowable<Boolean>
    fun fetchSelectedMirrorConfiguration(): Flowable<DataSnapshotWithKey<MirrorConfiguration>>
    fun fetchEnablePrecipitation(configurationKey: String): Flowable<Boolean>
    fun fetchScreenOrientation(configurationKey: String): Flowable<OrientationMode>
    fun fetchIsNeedToShowUserInfo(configurationKey: String): Flowable<Optional<TunerInfo>>
}