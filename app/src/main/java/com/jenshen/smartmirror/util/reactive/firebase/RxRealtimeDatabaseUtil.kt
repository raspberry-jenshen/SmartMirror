package com.jenshen.smartmirror.util.reactive.firebase

import com.google.firebase.database.*
import com.jenshen.smartmirror.data.firebase.FirebaseChildEvent
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


fun DatabaseReference.observeValue() : Flowable<DataSnapshot> {
    return Flowable.create<DataSnapshot>({ source ->
        val listener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                source.onNext(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                source.onError(error.toException())
            }
        }
        this.addValueEventListener(listener)
        source.setCancellable { this.removeEventListener(listener) }

    }, BackpressureStrategy.BUFFER)
}

fun DatabaseReference.loadValue() : Single<DataSnapshot> {
    return Single.create<DataSnapshot>({ source ->
        val listener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                source.onSuccess(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                source.onError(error.toException())
            }
        }
        this.addListenerForSingleValueEvent(listener)
        source.setCancellable { this.removeEventListener(listener) }
    })
}

fun DatabaseReference.uploadValue(any: Any): Completable {
    return Completable.create { source ->
        this.setValue(any)
                .addOnCompleteListener {
                    if (it.isComplete && it.isSuccessful) {
                        source.onComplete()
                    } else {
                        source.onError(it.exception)
                    }
                }
    }
}

fun DatabaseReference.updateValues(map: Map<String, Any>): Completable {
    return Completable.create { source ->
        this.updateChildren(map)
                .addOnCompleteListener {
                    if (it.isComplete && it.isSuccessful) {
                        source.onComplete()
                    } else {
                        source.onError(it.exception)
                    }
                }
    }
}

fun DatabaseReference.observeChilds() : Flowable<FirebaseChildEvent> {
    return Flowable.create<FirebaseChildEvent>({ subscriber ->
        val listener = object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, prevName: String) {
                subscriber.onNext(FirebaseChildEvent(dataSnapshot, FirebaseChildEvent.CHILD_ADDED, prevName))
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, prevName: String) {
                subscriber.onNext(FirebaseChildEvent(dataSnapshot, FirebaseChildEvent.CHILD_CHANGED, prevName))
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                subscriber.onNext(FirebaseChildEvent(dataSnapshot, FirebaseChildEvent.CHILD_REMOVED))
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, prevName: String) {
                subscriber.onNext(FirebaseChildEvent(dataSnapshot, FirebaseChildEvent.CHILD_MOVED, prevName))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                subscriber.onError(databaseError.toException())
            }
        }
        this.addChildEventListener(listener)
        subscriber.setCancellable { this.removeEventListener(listener) }

    }, BackpressureStrategy.BUFFER)
}