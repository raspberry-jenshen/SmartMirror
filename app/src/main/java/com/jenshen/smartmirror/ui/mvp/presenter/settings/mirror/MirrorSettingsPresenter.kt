package com.jenshen.smartmirror.ui.mvp.presenter.settings.mirror

import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.event.OrientationModeSettings
import com.jenshen.smartmirror.data.event.PrecipitationSettings
import com.jenshen.smartmirror.data.event.UserInfoSettings
import com.jenshen.smartmirror.data.firebase.model.configuration.OrientationMode
import com.jenshen.smartmirror.data.model.configuration.ConfigurationSettingsModel
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.ui.mvp.view.settings.mirror.MirrorSettingsView
import com.jenshen.smartmirror.util.reactive.applyProgress
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Single
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class MirrorSettingsPresenter @Inject constructor(private val tunerApiInteractor: TunerApiInteractor) : MvpRxPresenter<MirrorSettingsView>() {

    fun updateModel(configurationKey: String) {
        Single.zip(tunerApiInteractor.isUserInfoOnMirror(configurationKey),
                tunerApiInteractor.isEnablePrecipitationOnMirror(configurationKey),
                tunerApiInteractor.getOrientationModeInConfiguration(configurationKey),
                Function3 { isUserInfoEnabled: Boolean, isEnablePrecipitation: Boolean, orientationMode: OrientationMode ->
                    ConfigurationSettingsModel(configurationKey, isUserInfoEnabled, isEnablePrecipitation, orientationMode)
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
                .subscribe({ EventBus.getDefault().post(PrecipitationSettings(isEnable)) }, { view?.handleError(it) })
    }

    fun enableUserInfoOnMirror(configurationKey: String, isEnable: Boolean) {
        tunerApiInteractor.setEnableUserInfoOnMirror(configurationKey, isEnable)
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ EventBus.getDefault().post(UserInfoSettings(if (isEnable) it else null)) }, { view?.handleError(it) })
    }

    fun setOrientationMode(configurationKey: String, orientationMode: OrientationMode) {
        tunerApiInteractor.setOrientationModeInConfiguration(configurationKey, orientationMode)
                .applySchedulers(Schedulers.io())
                .applyProgress(Consumer { view?.showProgress() }, Action { view?.hideProgress() })
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({
                    EventBus.getDefault().post(OrientationModeSettings(orientationMode))
                    view?.onOrientationChanged(orientationMode)
                }, { view?.handleError(it) })
    }
}