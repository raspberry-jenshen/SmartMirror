package com.jenshen.smartmirror.interactor.firebase.api.tuner

import io.reactivex.Completable

interface TunerApiInteractor {

    fun subscribeOnMirror(mirrorId: String): Completable
}