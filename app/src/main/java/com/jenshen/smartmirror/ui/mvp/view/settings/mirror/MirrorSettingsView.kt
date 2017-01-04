package com.jenshen.smartmirror.ui.mvp.view.settings.mirror

import com.jenshen.compat.base.view.BaseMvpView
import com.jenshen.smartmirror.data.firebase.model.configuration.OrientationMode
import com.jenshen.smartmirror.data.model.configuration.ConfigurationSettingsModel

interface MirrorSettingsView : BaseMvpView {
    fun onModelUpdated(model: ConfigurationSettingsModel)
    fun onOrientationChanged(orientationMode: OrientationMode)
}