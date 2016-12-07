package com.jenshen.smartmirror.data.model

import com.jenshen.smartmirror.data.firebase.model.mirror.MirrorConfigurationInfo
import com.jenshen.smartmirror.data.firebase.model.tuner.TunerSubscription
import java.util.*


data class MirrorModel(val key: String,
                       val tunerSubscription: TunerSubscription,
                       val isRemoved: Boolean) {

    var checkedConfigurationKey: String? = null
    var mirrorConfigurationInfo: HashMap<String, MirrorConfigurationInfo>? = null

    override fun equals(other: Any?): Boolean {
        if (other != null && other is MirrorModel) {
            return tunerSubscription.id == other.tunerSubscription.id
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return tunerSubscription.hashCode()
    }
}
