package com.jenshen.smartmirror.data.firebase.model.configuration

import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant


class ContainerSize(@set:PropertyName(FirebaseConstant.MirrorConfiguration.ContainerSize.COLUMNS_COUNT)
                    @get:PropertyName(FirebaseConstant.MirrorConfiguration.ContainerSize.COLUMNS_COUNT)
                    var column: Int = -1,
                    @set:PropertyName(FirebaseConstant.MirrorConfiguration.ContainerSize.ROWS_COUNT)
                    @get:PropertyName(FirebaseConstant.MirrorConfiguration.ContainerSize.ROWS_COUNT)
                    var row: Int = -1)
