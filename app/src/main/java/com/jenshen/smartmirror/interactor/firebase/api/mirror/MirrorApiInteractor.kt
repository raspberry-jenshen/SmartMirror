package com.jenshen.smartmirror.interactor.firebase.api.mirror

import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import io.reactivex.Flowable

interface MirrorApiInteractor {
    fun fetchIsNeedToShowQrCode(mirrorId: String): Flowable<Boolean>
    fun fetchSelectedMirrorConfiguration(mirrorId: String): Flowable<MirrorConfiguration>
}