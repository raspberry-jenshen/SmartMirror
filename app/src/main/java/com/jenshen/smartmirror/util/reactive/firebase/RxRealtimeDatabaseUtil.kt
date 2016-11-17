package com.jenshen.smartmirror.util.reactive.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Completable
import io.reactivex.Single


fun DatabaseReference.asRxGetValueEvent(): Single<DataSnapshot> {
    return Single.create<DataSnapshot> {
        val listener = this.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                it.onSuccess(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                it.onError(error.toException())
            }
        })
        it.setCancellable({ this.removeEventListener(listener) })
    }
}

fun DatabaseReference.asRxSetValue(any: Any): Completable {
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
