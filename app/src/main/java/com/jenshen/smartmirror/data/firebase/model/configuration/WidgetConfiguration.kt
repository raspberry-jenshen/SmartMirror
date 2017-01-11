package com.jenshen.smartmirror.data.firebase.model.configuration

import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant


class WidgetConfiguration(
        @set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.WIDGET_KEY)
        @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.WIDGET_KEY)
        var widgetKey: String,
        @set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.TOP_LEFT_CORNER)
        @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.TOP_LEFT_CORNER)
        var topLeftCorner: Corner,
        @set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.TOP_RIGHT_CORNER)
        @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.TOP_RIGHT_CORNER)
        var topRightCorner: Corner,
        @set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.BOTTOM_LEFT_CORNER)
        @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.BOTTOM_LEFT_CORNER)
        var bottomLeftCorner: Corner,
        @set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.BOTTOM_RIGHT_CORNER)
        @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.BOTTOM_RIGHT_CORNER)
        var bottomRightCorner: Corner,
        @set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.TUNER_KEY)
        @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.TUNER_KEY)
        var tunerKey: String?) {

    constructor() : this("", Corner(), Corner(), Corner(), Corner(), null) {
        // Default constructor required for calls to DataSnapshot.getValue(Widget.class)
    }
}
