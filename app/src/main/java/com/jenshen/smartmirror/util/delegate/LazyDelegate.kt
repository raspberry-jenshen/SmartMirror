package com.jenshen.smartmirror.util.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private class LazyDelegate<T>(initializer: () -> T) : ReadWriteProperty<Any?, T> {

    private var initializer: (() -> T)? = initializer

    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: initializer!!()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}

fun <T> lazyValue(initializer: () -> T): ReadWriteProperty<Any?, T> = LazyDelegate(initializer)