package com.jenshen.smartmirror.ui.mvp.view.choose.widget

import com.jenshen.compat.base.view.BaseLceMvpView
import com.jenshen.smartmirror.data.firebase.DataSnapshotWithKey
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo


interface ChooseWidgetView : BaseLceMvpView<DataSnapshotWithKey<WidgetInfo>> {
}