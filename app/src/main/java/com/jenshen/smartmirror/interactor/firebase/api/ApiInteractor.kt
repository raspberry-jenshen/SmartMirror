package com.jenshen.smartmirror.interactor.firebase.api

import com.jenshen.smartmirror.data.firebase.Mirror
import io.reactivex.Single

interface ApiInteractor {
    fun createOrGetMirror(id: String): Single<Mirror>
}