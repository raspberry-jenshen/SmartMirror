package com.jenshen.smartmirror.util.reactive

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

fun <T> Observable<T>.applySchedulers(scheduler: Scheduler): Observable<T> {
    return this.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.applySchedulers(scheduler: Scheduler): Single<T> {
    return this.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}