package com.jenshen.smartmirror.data.model.widget

import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo


class WidgetModel(val widgetInfo: WidgetInfo,
                  var widgetData: WidgetData,
                  var phrase: String? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as WidgetModel
        if (widgetData.widgetKey != other.widgetData.widgetKey) return false
        return true
    }

    override fun hashCode(): Int {
        return widgetData.widgetKey.hashCode()
    }
}
