package com.jenshen.smartmirror.interactor.firebase.api.tuner

import com.jenshen.smartmirror.data.model.MirrorModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface TunerApiInteractor {
    fun subscribeOnMirror(mirrorId: String): Completable
    fun fetchTunerSubscriptions(): Flowable<MirrorModel>
}