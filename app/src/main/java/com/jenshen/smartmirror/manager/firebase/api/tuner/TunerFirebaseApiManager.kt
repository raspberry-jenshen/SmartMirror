package com.jenshen.smartmirror.manager.firebase.api.tuner

import com.jenshen.smartmirror.data.firebase.FirebaseChildEvent
import com.jenshen.smartmirror.data.firebase.MirrorSubscriber
import com.jenshen.smartmirror.data.firebase.TunerSubscription
import com.jenshen.smartmirror.manager.firebase.database.RealtimeDatabaseManager
import com.jenshen.smartmirror.util.reactive.firebase.observeChilds
import com.jenshen.smartmirror.util.reactive.firebase.uploadValue
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class TunerFirebaseApiManager @Inject constructor(private val fireBaseDatabase: RealtimeDatabaseManager) : TunerApiManager {

    override fun addSubscriberToMirror(mirrorId: String): Completable {
        return fireBaseDatabase
                .getMirrorSubscribersRef(mirrorId)
                .map { it.push() }
                .flatMapCompletable { it.uploadValue(MirrorSubscriber(mirrorId).toValueWithUpdateTime()) }
    }

    override fun addSubscriptionToTuner(tunerId: String, mirrorId: String): Completable {
        return fireBaseDatabase
                .getTunerSubscriptionsRef(tunerId)
                .map { it.push() }
                .flatMapCompletable { it.uploadValue(TunerSubscription(mirrorId)) }
    }

    override fun setFlagForWaitingSubscribersOnMirror(isWaiting: Boolean, mirrorId: String): Completable {
        return fireBaseDatabase
                .getIsWaitingForTunerFlagRef(mirrorId)
                .flatMapCompletable { it.uploadValue(isWaiting) }
    }

    override fun observeTunerSubscriptions(id: String): Flowable<FirebaseChildEvent> {
        return fireBaseDatabase
                .getTunerSubscriptionsRef(id)
                .flatMapPublisher { it.observeChilds() }
                .filter { it.dataSnapshot.exists() }
    }
}