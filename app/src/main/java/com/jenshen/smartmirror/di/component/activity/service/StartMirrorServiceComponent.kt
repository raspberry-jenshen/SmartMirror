package com.jenshen.smartmirror.di.component.activity.service

import com.jenshen.smartmirror.di.module.activity.service.StartMirrorServiceModule
import com.jenshen.smartmirror.di.scope.ActivityScope
import com.jenshen.smartmirror.service.StartMirrorService
import dagger.MembersInjector
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = arrayOf(StartMirrorServiceModule::class))
interface StartMirrorServiceComponent : MembersInjector<StartMirrorService> {
}