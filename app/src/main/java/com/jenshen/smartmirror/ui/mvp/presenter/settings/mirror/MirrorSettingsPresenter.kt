package com.jenshen.smartmirror.ui.mvp.presenter.settings.mirror

import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.model.configuration.ConfigurationSettingsModel
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.ui.mvp.view.settings.mirror.MirrorSettingsView
import com.jenshen.smartmirror.util.reactive.applyProgress
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Single
import io.reactivex.functions.Action
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MirrorSettingsPresenter @Inject constructor(private val tunerApiInteractor: TunerApiInteractor) : MvpRxPresenter<MirrorSettingsView>() {

    fun updateModel(configurationKey: String) {
        Single.zip(tunerApiInteractor.isUserInfoOnMirror(configurationKey),
                tunerApiInteractor.isEnablePrecipitationOnMirror(configurationKey),
                BiFunction { isUserInfoEnabled: Boolean, isEnablePrecipitation: Boolean ->
                    ConfigurationSettingsModel(configurationKey, isUserInfoEnabled, isEnablePrecipitation)
                })
                .applySchedulers(Schedulers.io())
                .applyProgress(Consumer { view?.showProgress() }, Action { view?.hideProgress() })
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onModelUpdated(it) }, { view?.handleError(it) })
    }

    fun enablePrecipitationOnMirror(configurationKey: String, isEnable: Boolean) {
        tunerApiInteractor.setEnablePrecipitationOnMirror(configurationKey, isEnable)
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({}, { view?.handleError(it) })
    }

    fun enableUserInfoOnMirror(configurationKey: String, isEnable: Boolean) {
        tunerApiInteractor.setEnableUserInfoOnMirror(configurationKey, isEnable)
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({}, { view?.handleError(it) })
    }
}