package com.jenshen.smartmirror.manager.firebase.api

import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.firebase.FirebaseConstant
import com.jenshen.smartmirror.data.firebase.Mirror
import com.jenshen.smartmirror.data.firebase.Tuner
import com.jenshen.smartmirror.manager.firebase.database.RealtimeDatabaseManager
import com.jenshen.smartmirror.util.reactive.firebase.loadValue
import com.jenshen.smartmirror.util.reactive.firebase.observeValue
import com.jenshen.smartmirror.util.reactive.firebase.uploadValue
import io.reactivex.Flowable
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
                                reference.uploadValue(tuner)
                                        .toSingle { tuner }
                            }
                }
    }

    override fun getTuner(id: String): Maybe<Tuner> {
        return fireBaseDatabase.getTunerRef(id)
                .flatMap { it.loadValue() }
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
                                reference.uploadValue(mirror)
                                        .toSingle { mirror }
                            }
                }
    }

    override fun getMirror(id: String): Maybe<Mirror> {
        return fireBaseDatabase.getMirrorRef(id)
                .flatMap { it.loadValue() }
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

    override fun observeIsWaitingForTuner(id: String): Flowable<Boolean> {
        return fireBaseDatabase.getMirrorRef(id)
                .map { it.child(FirebaseConstant.Mirrors.IS_WAITING_FOR_TUNER) }
                .flatMapPublisher { it.observeValue() }
                .map { it.getValue(Boolean::class.java) }
    }
}
