package com.jenshen.smartmirror.interactor.firebase.api.mirror

import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.NullableDataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.data.firebase.model.tuner.TunerInfo
import io.reactivex.Flowable

interface MirrorApiInteractor {
    fun fetchIsNeedToShowQrCode(): Flowable<Boolean>
    fun fetchSelectedMirrorConfiguration(): Flowable<DataSnapshotWithKey<MirrorConfiguration>>
    fun fetchEnablePrecipitation(configurationKey: String): Flowable<Boolean>
    fun fetchIsNeedToShowUserInfo(configurationKey: String): Flowable<NullableDataSnapshotWithKey<TunerInfo>>
}