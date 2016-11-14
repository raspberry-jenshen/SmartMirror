package com.jenshen.smartmirror.util.reactive.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Single


fun DatabaseReference.asValueEventSingle(): Single<DataSnapshot> {
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
