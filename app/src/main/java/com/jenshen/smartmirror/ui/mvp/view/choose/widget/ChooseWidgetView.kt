package com.jenshen.smartmirror.ui.mvp.view.choose.widget

import com.jenshen.compat.base.view.BaseLceMvpView
import com.jenshen.smartmirror.data.entity.widget.info.InfoForWidget
import com.jenshen.smartmirror.data.model.WidgetModel


interface ChooseWidgetView : BaseLceMvpView<WidgetModel> {
    fun onWidgetUpdate(position: Int, infoForWidget: InfoForWidget)
}