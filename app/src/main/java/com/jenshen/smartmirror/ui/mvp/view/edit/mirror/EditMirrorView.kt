package com.jenshen.smartmirror.ui.mvp.view.edit.mirror

import com.jenshen.compat.base.view.BaseMvpView
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.model.mirror.EditMirrorModel

interface EditMirrorView : BaseMvpView {

    fun onSavedConfiguration()
    fun onMirrorConfigurationLoaded(model: EditMirrorModel)
    fun onWidgetUpdate(infoData: WidgetData)
}