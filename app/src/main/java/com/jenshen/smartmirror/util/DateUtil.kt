package com.jenshen.smartmirror.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        val BASE_DATE_FORMAT = "dd.MM.yyyy"
        val DAT_MONTH = "dd.MM"
    }
}

fun Date.toDayMonthYear(): String {
    val baseFormat = SimpleDateFormat(DateUtil.BASE_DATE_FORMAT, Locale.getDefault())
    return baseFormat.format(this)
}

fun Date.toDayMonth(): String {
    val baseFormat = SimpleDateFormat(DateUtil.DAT_MONTH, Locale.getDefault())
    return baseFormat.format(this)
}