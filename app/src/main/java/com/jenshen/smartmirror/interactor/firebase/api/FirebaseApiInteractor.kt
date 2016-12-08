package com.jenshen.smartmirror.interactor.firebase.api

import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.firebase.model.mirror.Mirror
import com.jenshen.smartmirror.data.firebase.model.tuner.Tuner
import com.jenshen.smartmirror.manager.firebase.api.ApiManager
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class FirebaseApiInteractor @Inject constructor(private val apiManager: ApiManager) : ApiInteractor {

    override fun createOrGetTuner(tunerSession: TunerSession): Single<Tuner> {
        return apiManager.getTuner(tunerSession.key)
                .switchIfEmpty(apiManager.createTuner(tunerSession).toMaybe())
                .toSingle()
    }

    override fun createOrGetMirror(mirrorSession: MirrorSession): Single<Mirror> {
        return apiManager.getMirror(mirrorSession.key)
                .switchIfEmpty(apiManager.createMirror(mirrorSession).toMaybe())
                .toSingle()
    }

    override fun isTunerConnected(id : String): Flowable<Boolean> {
        return apiManager.observeIsWaitingForTuner(id)
                .filter { it == false }
    }
}