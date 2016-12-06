package com.jenshen.smartmirror.data.firebase.model.configuration

import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant


class WidgetConfiguration(@set:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.WIDGET_KEY)
             @get:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.WIDGET_KEY)
             var key: String,
                          @set:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.TOP_LEFT_CORNER)
             @get:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.TOP_LEFT_CORNER)
             var topLeftCorner: Corner,
                          @set:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.TOP_RIGHT_CORNER)
             @get:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.TOP_RIGHT_CORNER)
             var topRightCorner: Corner,
                          @set:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.BOTTOM_LEFT_CORNER)
             @get:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.BOTTOM_LEFT_CORNER)
             var bottomLeftCorner: Corner,
                          @set:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.BOTTOM_RIGHT_CORNER)
             @get:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.BOTTOM_RIGHT_CORNER)
             var bottomRightCorner: Corner) {

    constructor() : this("", Corner(), Corner(), Corner(), Corner()) {
        // Default constructor required for calls to DataSnapshot.getValue(Widget.class)
    }
}
