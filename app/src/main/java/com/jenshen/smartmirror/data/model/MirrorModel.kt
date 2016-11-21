package com.jenshen.smartmirror.data.model

import com.jenshen.smartmirror.data.firebase.TunerSubscription


data class MirrorModel(private val tunerSubscription: TunerSubscription, private val isRemoved:Boolean) {

}
