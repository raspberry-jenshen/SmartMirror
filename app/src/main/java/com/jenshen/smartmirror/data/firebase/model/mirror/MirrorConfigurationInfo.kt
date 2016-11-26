package com.jenshen.smartmirror.data.firebase.model.mirror

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant

@IgnoreExtraProperties
data class MirrorConfigurationInfo(@set:PropertyName(FirebaseConstant.Mirror.MirrorConfigurationInfo.TITLE)
                                   @get:PropertyName(FirebaseConstant.Mirror.MirrorConfigurationInfo.TITLE)
                                   var id: String) {
    @set:PropertyName(FirebaseConstant.MirrorConfiguration.LAST_TIME_UPDATE)
    @get:PropertyName(FirebaseConstant.MirrorConfiguration.LAST_TIME_UPDATE)
    var lastTimeUpdate: Long? = null

    constructor() : this("") {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}