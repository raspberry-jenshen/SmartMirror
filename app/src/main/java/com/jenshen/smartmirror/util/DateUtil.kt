package com.jenshen.smartmirror.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        private val BASE_DATE_FORMAT = "dd.MM.yyyy"
        val baseFormat = SimpleDateFormat(BASE_DATE_FORMAT, Locale.getDefault())
    }
}

fun Date.toSimpleFormat(): String {
    return DateUtil.baseFormat.format(this)
}