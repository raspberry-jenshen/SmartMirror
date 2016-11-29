package com.jenshen.smartmirror.data.firebase.model.widget

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant

@IgnoreExtraProperties
data class Size(@set:PropertyName(FirebaseConstant.Widget.Size.WIDTH)
                @get:PropertyName(FirebaseConstant.Widget.Size.WIDTH)
                var width: Int = 0,
                @set:PropertyName(FirebaseConstant.Widget.Size.HEIGHT)
                @get:PropertyName(FirebaseConstant.Widget.Size.HEIGHT)
                var height: Int = 0)