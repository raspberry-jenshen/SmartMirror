package com.jenshen.smartmirror.util.reactive

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import org.reactivestreams.Subscription

/* schedulers */

fun <T> Observable<T>.applySchedulers(scheduler: Scheduler): Observable<T> {
    return this.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.applySchedulers(scheduler: Scheduler): Flowable<T> {
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

/* progress */

fun <T> Observable<T>.applyProgress(onSubscribe: Consumer<in Disposable>, action: Action): Observable<T> {
    return this.doOnSubscribe(onSubscribe).doFinally(action)
}

fun <T> Flowable<T>.applyProgress(onSubscribe: Consumer<in Subscription>, action: Action): Flowable<T> {
    return this.doOnSubscribe(onSubscribe).doFinally(action)
}

fun <T> Single<T>.applyProgress(onSubscribe: Consumer<in Disposable>, action: Action): Single<T> {
    return this.doOnSubscribe(onSubscribe).doFinally(action)
}

fun <T> Maybe<T>.applyProgress(onSubscribe: Consumer<in Disposable>, action: Action): Maybe<T> {
    return this.doOnSubscribe(onSubscribe).doFinally(action)
}

fun Completable.applyProgress(onSubscribe: Consumer<in Disposable>, action: Action): Completable {
    return this.doOnSubscribe(onSubscribe).doFinally(action)
}