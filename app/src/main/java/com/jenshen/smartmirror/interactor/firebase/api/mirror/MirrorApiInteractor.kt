package com.jenshen.smartmirror.interactor.firebase.api.mirror

import io.reactivex.Flowable

interface MirrorApiInteractor {
    fun fetchIsNeedToShoQrCode(mirrorId: String): Flowable<Boolean>
}