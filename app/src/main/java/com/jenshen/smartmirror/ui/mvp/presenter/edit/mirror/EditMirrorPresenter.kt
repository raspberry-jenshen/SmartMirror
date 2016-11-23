package com.jenshen.smartmirror.ui.mvp.presenter.edit.mirror


import com.jenshen.compat.base.presenter.MvpLceRxPresenter
import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.firebase.model.tuner.TunerSubscription
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.ui.mvp.view.choose.mirror.ChooseMirrorView
import com.jenshen.smartmirror.ui.mvp.view.edit.mirror.EditMirrorView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EditMirrorPresenter @Inject constructor() : MvpRxPresenter<EditMirrorView>() {
}
