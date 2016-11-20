package com.jenshen.smartmirror.interactor.firebase.api

import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.firebase.Mirror
import com.jenshen.smartmirror.data.firebase.Tuner
import io.reactivex.Flowable
import io.reactivex.Single

interface ApiInteractor {
    fun createOrGetTuner(tunerSession: TunerSession): Single<Tuner>
    fun createOrGetMirror(mirrorSession: MirrorSession): Single<Mirror>
    fun isTunerConnected(id: String): Flowable<Boolean>
}