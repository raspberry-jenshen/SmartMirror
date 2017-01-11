package com.jenshen.smartmirror.manager.widget.factory

import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.data.updater.WidgetUpdater
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshensoft.widgetview.WidgetView

interface IWidgetFactoryManager {
    fun getUpdaterForWidget(widgetKey: WidgetKey, tunerKey: String? = null): WidgetUpdater<*>
    fun updateWidget(infoData: WidgetData, widget: Widget<*>)
}
