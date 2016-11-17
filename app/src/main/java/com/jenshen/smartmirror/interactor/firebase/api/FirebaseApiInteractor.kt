package com.jenshen.smartmirror.interactor.firebase.api

import com.jenshen.smartmirror.data.firebase.Mirror
import com.jenshen.smartmirror.manager.firebase.api.ApiManager
import io.reactivex.Single
import javax.inject.Inject

class FirebaseApiInteractor @Inject constructor(private val apiManager: ApiManager) : ApiInteractor {

    override fun createOrGetMirror(id: String): Single<Mirror> {
        return apiManager.getMirror(id)
                .switchIfEmpty(apiManager.createMirror(id).toMaybe())
                .toSingle()
    }
}