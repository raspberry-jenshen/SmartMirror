package com.jenshen.smartmirror.interactor.firebase.api.tuner

import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo
import com.jenshen.smartmirror.data.model.EditMirrorModel
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.data.model.WidgetConfigurationModel
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

    fun fetchWidgets(): Flowable<DataSnapshotWithKey<WidgetInfo>>
    fun addWidget(name: String, width: Int, height: Int): Single<String>

    /* mirror configurations */
    fun saveMirrorConfiguration(editMirrorModel: EditMirrorModel): Completable
    fun getMirrorConfiguration(configurationKey: String): Single<EditMirrorModel>
}