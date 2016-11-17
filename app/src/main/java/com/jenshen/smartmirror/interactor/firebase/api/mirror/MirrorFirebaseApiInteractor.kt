package com.jenshen.smartmirror.interactor.firebase.api.mirror

import com.jenshen.smartmirror.manager.firebase.api.mirror.MirrorApiManager
import javax.inject.Inject

class MirrorFirebaseApiInteractor : MirrorApiInteractor {

    private var apiManager: MirrorApiManager

    @Inject
    constructor(apiManager: MirrorApiManager) {
        this.apiManager = apiManager
    }
}