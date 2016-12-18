package com.jenshen.smartmirror.data.model.widget

import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo


data class WidgetModel(val widgetDataSnapshot: DataSnapshotWithKey<WidgetInfo>,
                       var widgetData: WidgetData? = null)
