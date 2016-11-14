package com.jenshen.smartmirror.util.reactive

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers

fun <T> Observable<T>.applySchedulers(scheduler: Scheduler): Observable<T> {
    return this.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.applySchedulers(scheduler: Scheduler): Single<T> {
    return this.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.applySchedulers(scheduler: Scheduler): Maybe<T> {
    return this.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.applySchedulers(scheduler: Scheduler): Completable {
    return this.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}