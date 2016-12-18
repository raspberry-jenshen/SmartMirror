package com.jenshen.smartmirror.di.module.manager.widget.factory

import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.widget.factory.IWidgetFactoryManager
import com.jenshen.smartmirror.manager.widget.factory.WidgetFactoryManager
import dagger.Binds
import dagger.Module

@Module
abstract class WidgetFactoryManagerModule {

    @SessionScope
    @Binds
    abstract fun provideWidgetFactoryManager(widgetFactoryManager: WidgetFactoryManager): IWidgetFactoryManager
}