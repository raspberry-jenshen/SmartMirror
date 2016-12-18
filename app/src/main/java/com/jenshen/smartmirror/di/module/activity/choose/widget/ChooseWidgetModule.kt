package com.jenshen.smartmirror.di.module.activity.choose.widget

import com.jenshen.smartmirror.di.module.manager.widget.factory.WidgetFactoryManagerModule
import dagger.Module


@Module(includes = arrayOf(WidgetFactoryManagerModule::class))
class ChooseWidgetModule {
}