package com.jenshen.smartmirror.ui.mvp.view.dashboard.mirror

import com.jenshen.compat.base.view.BaseMvpView
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.firebase.NullableDataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.data.firebase.model.tuner.TunerInfo
import com.jenshen.smartmirror.data.model.widget.PrecipitationModel


interface MirrorDashboardView : BaseMvpView {
    fun showSignUpScreen()
    fun changeMirrorConfiguration(mirrorConfiguration: MirrorConfiguration)
    fun onWidgetUpdate(infoData: WidgetData)
    fun enablePrecipitation(enable: Boolean)
    fun enableUserInfo(userInfoData: NullableDataSnapshotWithKey<TunerInfo>)
    fun onPrecipitationUpdate(model: PrecipitationModel)
}