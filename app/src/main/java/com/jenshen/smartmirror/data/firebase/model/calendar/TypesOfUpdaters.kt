package com.jenshen.smartmirror.data.firebase.model.calendar

import android.support.annotation.StringDef


const val JOB_DISPATCHER = "jov_dispatcher"
const val UPDATE_CALENDAR_RECEIVER = "calendar_receiver"
const val AFTER_FIRST_ADD = "after_first_add"

@StringDef(JOB_DISPATCHER, UPDATE_CALENDAR_RECEIVER, AFTER_FIRST_ADD)
@Retention(AnnotationRetention.SOURCE)
annotation class TypesOfUpdaters