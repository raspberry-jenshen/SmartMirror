package com.jenshen.smartmirror.interactor.firebase.api.tuner

import com.jenshen.smartmirror.data.firebase.model.configuration.OrientationMode
import com.jenshen.smartmirror.data.model.mirror.EditMirrorModel
import com.jenshen.smartmirror.data.model.mirror.MirrorModel
import com.jenshen.smartmirror.data.model.widget.WidgetModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface TunerApiInteractor {

    /* mirror */

    fun subscribeOnMirror(mirrorId: String): Completable
    fun unsubscribeFromMirror(mirrorId: String): Completable
    fun switchFlagForWaitingTuner(mirrorId: String): Completable
    fun setSelectedConfigurationKeyForMirror(configurationId: String?, mirrorId: String): Completable
    fun deleteConfigurationForMirror(configurationId: String, mirrorId: String, isSelected: Boolean): Completable

    /* tuner */

    fun fetchTunerSubscriptions(): Flowable<MirrorModel>

    /* widget */

    fun fetchWidgets(): Flowable<WidgetModel>
    fun addWidget(name: String, width: Int, height: Int): Single<String>

    /* mirror configurations */
    fun saveMirrorConfiguration(editMirrorModel: EditMirrorModel): Completable
    fun getMirrorConfiguration(configurationKey: String): Single<EditMirrorModel>

    fun setOrientationModeInConfiguration(configurationKey: String, orientationMode: OrientationMode): Completable
    fun getOrientationModeInConfiguration(configurationKey: String): Single<OrientationMode>

    fun setEnablePrecipitationOnMirror(configurationKey: String, isEnable: Boolean): Completable
    fun isEnablePrecipitationOnMirror(configurationKey: String): Single<Boolean>

    fun setEnableUserInfoOnMirror(configurationKey: String, isEnable: Boolean): Single<String>
    fun isUserInfoOnMirror(configurationKey: String): Single<Boolean>
}