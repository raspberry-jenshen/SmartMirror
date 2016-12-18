package com.jenshen.smartmirror.ui.mvp.view.choose.widget

import com.jenshen.compat.base.view.BaseLceMvpView
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetModel


interface ChooseWidgetView : BaseLceMvpView<WidgetModel> {
    fun onWidgetUpdate(widgetData: WidgetData)
}