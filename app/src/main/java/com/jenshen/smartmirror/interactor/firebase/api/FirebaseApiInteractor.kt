package com.jenshen.smartmirror.interactor.firebase.api

import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.firebase.Mirror
import com.jenshen.smartmirror.data.firebase.Tuner
import com.jenshen.smartmirror.manager.firebase.api.ApiManager
import io.reactivex.Single
import javax.inject.Inject

class FirebaseApiInteractor @Inject constructor(private val apiManager: ApiManager) : ApiInteractor {

    override fun createOrGetTuner(tunerSession: TunerSession): Single<Tuner> {
        return apiManager.getTuner(tunerSession.id)
                .switchIfEmpty(apiManager.createTuner(tunerSession).toMaybe())
                .toSingle()
    }

    override fun createOrGetMirror(mirrorSession: MirrorSession): Single<Mirror> {
        return apiManager.getMirror(mirrorSession.id)
                .switchIfEmpty(apiManager.createMirror(mirrorSession).toMaybe())
                .toSingle()
    }
}