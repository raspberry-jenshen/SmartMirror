package com.jenshen.smartmirror.data.firebase.model.configuration

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import java.util.*

@IgnoreExtraProperties
data class MirrorConfiguration(
        @set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.MIRROR_KEY)
        @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.MIRROR_KEY)
        var mirrorKey: String,
        @set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.TITLE)
        @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.TITLE)
        var title: String,
        @set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.CONTAINER_SIZE)
        @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.CONTAINER_SIZE)
        var containerSize: ContainerSize) {

    @set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.WIDGETS)
    @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.WIDGETS)
    var widgets: HashMap<String, WidgetConfiguration>? = null

    constructor() : this("", "", ContainerSize()) {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}