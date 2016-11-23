package com.jenshen.smartmirror.ui.mvp.view.signup.mirror

import android.graphics.Bitmap
import com.jenshen.compat.base.view.BaseMvpView
import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.data.firebase.model.mirror.Mirror

interface SignUpMirrorView : BaseMvpView {
    fun onMirrorCreated(mirror: Mirror, mirrorSession: MirrorSession, bitmap: Bitmap?)
    fun onTunerConnected()
}