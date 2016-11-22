package com.jenshen.smartmirror.data.model

import com.jenshen.smartmirror.data.firebase.model.TunerSubscription


data class MirrorModel(val tunerSubscription: TunerSubscription, val isRemoved:Boolean) {


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
