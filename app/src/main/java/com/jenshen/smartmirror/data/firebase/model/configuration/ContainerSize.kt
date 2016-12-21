package com.jenshen.smartmirror.data.firebase.model.configuration

import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant


class ContainerSize(@set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.ContainerSize.COLUMNS_COUNT)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.ContainerSize.COLUMNS_COUNT)
                    var column: Int = -1,
                    @set:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.ContainerSize.ROWS_COUNT)
                    @get:PropertyName(FirebaseRealTimeDatabaseConstant.MirrorConfiguration.ContainerSize.ROWS_COUNT)
                    var row: Int = -1)
