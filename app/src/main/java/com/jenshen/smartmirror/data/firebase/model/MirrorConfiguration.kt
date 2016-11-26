package com.jenshen.smartmirror.data.firebase.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant

@IgnoreExtraProperties
class MirrorConfiguration(@set:PropertyName(FirebaseConstant.MirrorConfiguration.MIRROR_ID)
                          @get:PropertyName(FirebaseConstant.MirrorConfiguration.MIRROR_ID)
                          var mirrorId: String?) {
    @set:PropertyName(FirebaseConstant.MirrorConfiguration.LAST_TIME_UPDATE)
    @get:PropertyName(FirebaseConstant.MirrorConfiguration.LAST_TIME_UPDATE)
    var lastTimeUpdate: Long? = null

    constructor() : this("") {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}