package com.jenshen.smartmirror.manager.firebase.api

import com.jenshen.smartmirror.data.firebase.Mirror
import io.reactivex.Maybe
import io.reactivex.Single


interface ApiManager {
    fun getMirror(id: String): Maybe<Mirror>
    fun createMirror(id: String): Single<Mirror>
}
