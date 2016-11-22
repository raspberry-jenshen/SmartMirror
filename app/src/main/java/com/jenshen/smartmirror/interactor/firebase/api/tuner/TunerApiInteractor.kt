package com.jenshen.smartmirror.interactor.firebase.api.tuner

import com.jenshen.smartmirror.data.model.MirrorModel
import io.reactivex.Completable
import io.reactivex.Flowable

interface TunerApiInteractor {
    fun subscribeOnMirror(mirrorId: String): Completable
    fun fetchTunerSubscriptions(): Flowable<MirrorModel>
    fun switchFlagForWaitingTuner(mirrorId: String): Completable
    fun unsubscribeFromMirror(mirrorId: String): Completable
}