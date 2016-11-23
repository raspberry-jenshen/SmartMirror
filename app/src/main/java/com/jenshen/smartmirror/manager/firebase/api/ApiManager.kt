package com.jenshen.smartmirror.manager.firebase.api

import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.firebase.model.mirror.Mirror
import com.jenshen.smartmirror.data.firebase.model.tuner.Tuner
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single


interface ApiManager {

    fun getTuner(id: String): Maybe<Tuner>
    fun createTuner(tunerSession: TunerSession): Single<Tuner>

    fun getMirror(id: String): Maybe<Mirror>
    fun createMirror(mirrorSession: MirrorSession): Single<Mirror>

    fun observeIsWaitingForTuner(id: String): Flowable<Boolean>
}
