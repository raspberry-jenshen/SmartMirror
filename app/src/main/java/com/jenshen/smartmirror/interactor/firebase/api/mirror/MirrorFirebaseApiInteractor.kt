package com.jenshen.smartmirror.interactor.firebase.api.mirror

import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.manager.firebase.api.ApiManager
import com.jenshen.smartmirror.manager.firebase.api.mirror.MirrorApiManager
import io.reactivex.Flowable
import javax.inject.Inject

class MirrorFirebaseApiInteractor @Inject constructor(private val apiManager: ApiManager, private val mirrorApiManager: MirrorApiManager) : MirrorApiInteractor {


    override fun fetchIsNeedToShowQrCode(mirrorId: String): Flowable<Boolean> {
        return apiManager.observeIsWaitingForTuner(mirrorId)
                .filter { it == true }
    }

    override fun fetchSelectedMirrorConfiguration(mirrorId: String): Flowable<MirrorConfiguration> {
        return mirrorApiManager.observeSelectedConfigurationKey(mirrorId)
                .flatMap {configurationKey -> mirrorApiManager.observeMirrorConfigurationInfoForMirror(mirrorId, configurationKey)
                        .flatMapSingle { mirrorApiManager.getMirrorConfiguration(configurationKey) }}
                .map { it.data }
    }
}