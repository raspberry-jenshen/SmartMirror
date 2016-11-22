package com.jenshen.smartmirror.interactor.firebase.api.mirror

import com.jenshen.smartmirror.manager.firebase.api.ApiManager
import io.reactivex.Flowable
import javax.inject.Inject

class MirrorFirebaseApiInteractor @Inject constructor(private val apiManager: ApiManager) : MirrorApiInteractor {


    override fun fetchIsNeedToShoQrCode(mirrorId: String): Flowable<Boolean> {
        return apiManager.observeIsWaitingForTuner(mirrorId)
                .filter { it == true }
    }
}