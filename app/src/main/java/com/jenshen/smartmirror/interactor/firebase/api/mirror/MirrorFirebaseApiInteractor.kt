package com.jenshen.smartmirror.interactor.firebase.api.mirror

import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.data.firebase.model.configuration.OrientationMode
import com.jenshen.smartmirror.data.firebase.model.tuner.TunerInfo
import com.jenshen.smartmirror.manager.firebase.api.ApiManager
import com.jenshen.smartmirror.manager.firebase.api.mirror.MirrorApiManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.util.Optional
import io.reactivex.Flowable
import javax.inject.Inject

class MirrorFirebaseApiInteractor @Inject constructor(private val apiManager: ApiManager,
                                                      private val preferencesManager: PreferencesManager,
                                                      private val mirrorApiManager: MirrorApiManager) : MirrorApiInteractor {

    override fun fetchIsNeedToShowQrCode(): Flowable<Boolean> {
        return apiManager.observeIsWaitingForTuner(preferencesManager.getSession()!!.key)
                .filter { it == true }
    }

    override fun fetchSelectedMirrorConfiguration(): Flowable<DataSnapshotWithKey<MirrorConfiguration>> {
        return mirrorApiManager.observeSelectedConfigurationKey(preferencesManager.getSession()!!.key)
                .flatMap {configurationKey -> mirrorApiManager.observeMirrorConfigurationInfoForMirror(preferencesManager.getSession()!!.key, configurationKey)
                        .flatMapSingle { mirrorApiManager.getMirrorConfiguration(configurationKey) }}
    }

    override fun fetchEnablePrecipitation(configurationKey: String): Flowable<Boolean> {
        return mirrorApiManager.observeIsEnablePrecipitation(configurationKey)
    }

    override fun fetchScreenOrientation(configurationKey: String): Flowable<OrientationMode> {
        return mirrorApiManager.observeScreenOrientation(configurationKey)
    }

    override fun fetchIsNeedToShowUserInfo(configurationKey: String): Flowable<Optional<TunerInfo>> {
        return mirrorApiManager.observeUserInfoOnMirror(configurationKey)
    }
}