package com.jenshen.smartmirror.data.model

import com.jenshen.smartmirror.data.entity.widget.info.InfoForWidget
import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo


data class WidgetModel(val widgetDataSnapshot: DataSnapshotWithKey<WidgetInfo>,
                       var infoForWidget: InfoForWidget? = null)
