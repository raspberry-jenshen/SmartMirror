package com.jenshen.smartmirror.ui.mvp.presenter.edit.mirror


import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.model.EditMirrorModel
import com.jenshen.smartmirror.interactor.firebase.api.tuner.TunerApiInteractor
import com.jenshen.smartmirror.ui.mvp.view.edit.mirror.EditMirrorView
import javax.inject.Inject

class EditMirrorPresenter @Inject constructor(private val tunerApiInteractor: TunerApiInteractor) : MvpRxPresenter<EditMirrorView>() {

    fun saveConfiguration(editMirrorModel: EditMirrorModel) {
        if (editMirrorModel.configurationId != null) {
        } else {
            tunerApiInteractor.addConfiguration(editMirrorModel)
        }
    }
}
