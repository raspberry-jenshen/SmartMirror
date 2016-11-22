package com.jenshen.smartmirror.manager.firebase.api.tuner

import com.jenshen.smartmirror.data.firebase.FirebaseChildEvent
import com.jenshen.smartmirror.data.firebase.model.Mirror
import com.jenshen.smartmirror.data.firebase.model.MirrorSubscriber
import com.jenshen.smartmirror.data.firebase.model.TunerSubscription
import com.jenshen.smartmirror.manager.firebase.database.RealtimeDatabaseManager
import com.jenshen.smartmirror.util.reactive.firebase.clearValue
import com.jenshen.smartmirror.util.reactive.firebase.observeChildren
import com.jenshen.smartmirror.util.reactive.firebase.uploadValue
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class TunerFirebaseApiManager @Inject constructor(private val fireBaseDatabase: RealtimeDatabaseManager) : TunerApiManager {

    override fun addSubscriberToMirror(tunerId: String, mirrorId: String): Completable {
        return fireBaseDatabase
                .getMirrorSubscribersRef(mirrorId)
                .map { it.child(tunerId) }
                .flatMapCompletable { it.uploadValue(MirrorSubscriber(tunerId).toValueWithUpdateTime()) }
    }

    override fun removeSubscriberFromMirror(tunerId: String, mirrorId: String): Completable {
        return fireBaseDatabase
                .getMirrorSubscribersRef(mirrorId)
                .flatMapCompletable { it.clearValue() }
    }

    override fun addSubscriptionToTuner(tunerId: String, mirrorId: String, mirror: Mirror): Completable {
        return fireBaseDatabase
                .getTunerSubscriptionsRef(tunerId)
                .map { it.child(mirrorId) }
                .flatMapCompletable { it.uploadValue(TunerSubscription(mirrorId, mirror.deviceInfo)) }
    }

    override fun removeSubscriptionFromTuner(tunerId: String, mirrorId: String): Completable {
        return fireBaseDatabase
                .getTunerSubscriptionsRef(tunerId)
                .map { it.child(mirrorId) }
                .flatMapCompletable { it.clearValue() }
    }

    override fun setFlagForWaitingSubscribersOnMirror(isWaiting: Boolean, mirrorId: String): Completable {
        return fireBaseDatabase
                .getIsWaitingForTunerFlagRef(mirrorId)
                .flatMapCompletable { it.uploadValue(isWaiting) }
    }

    override fun observeTunerSubscriptions(id: String): Flowable<FirebaseChildEvent> {
        return fireBaseDatabase
                .getTunerSubscriptionsRef(id)
                .flatMapPublisher { it.observeChildren() }
                .filter { it.dataSnapshot.exists() }
    }
}