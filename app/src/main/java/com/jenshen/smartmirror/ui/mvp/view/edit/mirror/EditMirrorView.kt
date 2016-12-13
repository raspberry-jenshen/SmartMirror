package com.jenshen.smartmirror.ui.mvp.view.edit.mirror

import com.jenshen.compat.base.view.BaseMvpView
import com.jenshen.smartmirror.data.model.EditMirrorModel

interface EditMirrorView : BaseMvpView {

    fun onSavedConfiguration()
    fun onMirrorConfigurationLoaded(model: EditMirrorModel)
}