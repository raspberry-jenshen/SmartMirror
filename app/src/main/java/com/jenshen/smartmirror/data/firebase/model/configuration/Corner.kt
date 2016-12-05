package com.jenshen.smartmirror.data.firebase.model.configuration

import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant


class Corner(@set:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.Corner.COLUMN)
             @get:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.Corner.COLUMN)
             var column: Int = -1,
             @set:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.Corner.ROW)
             @get:PropertyName(FirebaseConstant.MirrorConfiguration.Widget.Corner.ROW)
             var row: Int = -1)
