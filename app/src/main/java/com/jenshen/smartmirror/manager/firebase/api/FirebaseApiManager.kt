package com.jenshen.smartmirror.manager.firebase.api

import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.firebase.Mirror
import com.jenshen.smartmirror.data.firebase.Tuner
import com.jenshen.smartmirror.manager.firebase.database.RealtimeDatabaseManager
import com.jenshen.smartmirror.util.reactive.firebase.asRxGetValueEvent
import com.jenshen.smartmirror.util.reactive.firebase.asRxSetValue
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject


class FirebaseApiManager @Inject constructor(realtimeDatabaseManager: RealtimeDatabaseManager) : ApiManager {

    private val fireBaseDatabase: RealtimeDatabaseManager = realtimeDatabaseManager

    override fun createTuner(tunerSession: TunerSession): Single<Tuner> {
        return fireBaseDatabase.getTunerRef(tunerSession.id)
                .flatMap { reference ->
                    Single.fromCallable { Tuner(tunerSession.email) }
                            .flatMap { tuner ->
                                reference.asRxSetValue(tuner)
                                        .toSingle { tuner }
                            }
                }
    }

    override fun getTuner(id: String): Maybe<Tuner> {
        return fireBaseDatabase.getTunerRef(id)
                .flatMap { it.asRxGetValueEvent() }
                .flatMapMaybe { data ->
                    Maybe.create<Tuner> {
                        if (data.exists()) {
                            it.onSuccess(data.getValue(Tuner::class.java))
                        } else {
                            it.onComplete()
                        }
                    }
                }
    }

    override fun createMirror(mirrorSession: MirrorSession): Single<Mirror> {
        return fireBaseDatabase.getMirrorRef(mirrorSession.id)
                .flatMap { reference ->
                    Single.fromCallable { Mirror() }
                            .flatMap { mirror ->
                                reference.asRxSetValue(mirror)
                                        .toSingle { mirror }
                            }
                }
    }

    override fun getMirror(id: String): Maybe<Mirror> {
        return fireBaseDatabase.getMirrorRef(id)
                .flatMap { it.asRxGetValueEvent() }
                .flatMapMaybe { data ->
                    Maybe.create<Mirror> {
                        if (data.exists()) {
                            it.onSuccess(data.getValue(Mirror::class.java))
                        } else {
                            it.onComplete()
                        }
                    }
                }
    }
}
