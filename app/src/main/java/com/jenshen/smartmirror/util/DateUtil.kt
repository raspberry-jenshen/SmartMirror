package com.jenshen.smartmirror.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        val DAY_MONTH_YEAR = "dd.MM.yyyy"
        val DAT_MONTH = "dd.MM"
        val HOURS_MINUTES = "HH:mm"
    }
}

fun Date.toDayMonthYear(): String {
    val baseFormat = SimpleDateFormat(DateUtil.DAY_MONTH_YEAR, Locale.getDefault())
    return baseFormat.format(this)
}

fun Date.toDayMonth(): String {
    val baseFormat = SimpleDateFormat(DateUtil.DAT_MONTH, Locale.getDefault())
    return baseFormat.format(this)
}

fun Date.toHoursMinutes(): String {
    val baseFormat = SimpleDateFormat(DateUtil.HOURS_MINUTES, Locale.getDefault())
    return baseFormat.format(this)
}