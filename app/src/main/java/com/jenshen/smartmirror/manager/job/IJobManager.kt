package com.jenshen.smartmirror.manager.job

import com.jenshen.smartmirror.data.model.widget.WidgetKey
import io.reactivex.Completable

interface IJobManager {
    fun onCreateJob(mirrorKey: String,
                    configurationKey: String,
                    currentWidgetKey: String,
                    widgetKey: WidgetKey) : Completable
    fun onDeleteJob(mirrorKey: String,
                    configurationKey: String? = null,
                    currentWidgetKey: String? = null,
                    widgetKey: WidgetKey? = null): Completable
}