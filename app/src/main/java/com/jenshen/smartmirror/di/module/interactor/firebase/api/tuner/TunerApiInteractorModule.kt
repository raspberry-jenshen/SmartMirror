package com.jenshen.smartmirror.di.module.interactor.firebase.api.tuner

import android.content.Context
import com.jenshen.smartmirror.di.module.manager.job.CalendarJobManagerModule
import com.jenshen.smartmirror.di.module.manager.firebase.api.tuner.TunerApiManagerModule
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerFirebaseApiInteractor
import com.jenshen.smartmirror.manager.job.IJobManager
import com.jenshen.smartmirror.manager.firebase.api.ApiManager
import com.jenshen.smartmirror.manager.firebase.api.mirror.MirrorApiManager
import com.jenshen.smartmirror.manager.firebase.api.tuner.TunerApiManager
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(TunerApiManagerModule::class, CalendarJobManagerModule::class))
class TunerApiInteractorModule {

    @Provides
    fun bindTunerApiInteractor(context: Context,
                               apiManager: ApiManager,
                               preferencesManager: PreferencesManager,
                               tunerApiManager: TunerApiManager,
                               mirrorApiManager: MirrorApiManager,
                               jobManager: IJobManager): TunerApiInteractor {
        return TunerFirebaseApiInteractor(context, apiManager, preferencesManager, tunerApiManager, mirrorApiManager, jobManager)
    }
}