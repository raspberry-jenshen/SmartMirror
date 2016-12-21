package com.jenshen.smartmirror.data.firebase.model.configuration

import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant


class Corner(@set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.Corner.COLUMN)
             @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.Corner.COLUMN)
             var column: Int = -1,
             @set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.Corner.ROW)
             @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.Widget.Corner.ROW)
             var row: Int = -1)
