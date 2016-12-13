package com.jenshen.smartmirror.manager.firebase.api.tuner

import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.FirebaseChildEvent
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.data.firebase.model.configuration.WidgetConfiguration
import com.jenshen.smartmirror.data.firebase.model.mirror.Mirror
import com.jenshen.smartmirror.data.firebase.model.mirror.MirrorConfigurationInfo
import com.jenshen.smartmirror.data.firebase.model.widget.Widget
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*


interface TunerApiManager {

    /* mirror */
    fun addSubscriberToMirror(tunerKey: String, mirrorKey: String): Completable
    fun removeSubscriberFromMirror(tunerKey: String, mirrorKey: String): Completable
    fun setFlagForWaitingSubscribersOnMirror(isWaiting: Boolean, mirrorKey: String): Completable
    fun getMirrorConfigurationsInfo(mirrorKey: String): Maybe<HashMap<String, MirrorConfigurationInfo>>
    fun getSelectedConfigurationKeyForMirror(mirrorKey: String): Maybe<String>
    fun setSelectedConfigurationKeyForMirror(configurationKey: String, mirrorKey: String): Completable
    fun deleteSelectedConfigurationKeyForMirror(mirrorKey: String): Completable
    fun deleteMirrorConfigurationInfoForMirror(configurationKey: String, mirrorId: String): Completable
    fun createOrEditMirrorConfigurationInfoForMirror(configurationKey: String, mirrorKey: String, configurationInfo: MirrorConfigurationInfo): Completable

    /* tuner */
    fun addSubscriptionInTuner(tunerId: String, mirrorId: String, mirror: Mirror): Completable
    fun updateSubscriptionInTuner(tunerKey: String, mirrorKey: String): Completable
    fun removeSubscriptionFromTuner(tunerId: String, mirrorId: String): Completable
    fun observeTunerSubscriptions(tunerId: String): Flowable<FirebaseChildEvent>

    /* widget */
    fun observeWidgets(): Flowable<FirebaseChildEvent>
    fun getWidget(widgetKey: String): Single<DataSnapshotWithKey<Widget>>
    fun addWidget(widget: Widget): Single<String>

    /* mirror configurations */
    fun createMirrorConfiguration(mirrorConfiguration: MirrorConfiguration): Single<String>
    fun editMirrorConfiguration(configurationsKey: String, mirrorConfiguration: MirrorConfiguration): Completable
    fun createWidgetInConfiguration(configurationsKey: String, widgetConfiguration: WidgetConfiguration): Single<String>
    fun editWidgetInConfiguration(configurationsKey: String, keyWidget: String, widgetConfiguration: WidgetConfiguration): Completable
    fun deleteMirrorConfiguration(configurationId: String): Completable
}