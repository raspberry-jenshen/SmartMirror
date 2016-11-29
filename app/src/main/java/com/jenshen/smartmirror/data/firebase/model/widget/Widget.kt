package com.jenshen.smartmirror.data.firebase.model.widget

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant


@IgnoreExtraProperties
data class Widget(@set:PropertyName(FirebaseConstant.Widget.NAME)
                  @get:PropertyName(FirebaseConstant.Widget.NAME)
                  var name: String = "widget",
                  @set:PropertyName(FirebaseConstant.Widget.DEFAULT_SIZE)
                  @get:PropertyName(FirebaseConstant.Widget.DEFAULT_SIZE)
                  var defaultSize: Size = Size()) {

}
