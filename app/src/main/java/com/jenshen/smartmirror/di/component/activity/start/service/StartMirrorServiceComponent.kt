package com.jenshen.smartmirror.di.component.activity.start.service

import com.jenshen.smartmirror.di.module.activity.start.service.StartMirrorServiceModule
import com.jenshen.smartmirror.service.StartMirrorService
import dagger.MembersInjector
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(StartMirrorServiceModule::class))
interface StartMirrorServiceComponent : MembersInjector<StartMirrorService> {
}