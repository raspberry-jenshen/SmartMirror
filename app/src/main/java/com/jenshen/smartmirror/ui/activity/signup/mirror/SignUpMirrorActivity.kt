package com.jenshen.smartmirror.ui.activity.signup.mirror

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.data.firebase.model.Mirror
import com.jenshen.smartmirror.di.component.activity.signUp.mirror.SignUpMirrorComponent
import com.jenshen.smartmirror.ui.activity.dashboard.mirror.MirrorDashboardActivity
import com.jenshen.smartmirror.ui.mvp.presenter.signup.mirror.SignUpMirrorPresenter
import com.jenshen.smartmirror.ui.mvp.view.signup.mirror.SignUpMirrorView
import kotlinx.android.synthetic.main.activity_sign_up_mirror.*
import java.util.*


class SignUpMirrorActivity : BaseDiMvpActivity<SignUpMirrorComponent, SignUpMirrorView, SignUpMirrorPresenter>(), SignUpMirrorView {


    /* inject */

    override fun createComponent(): SignUpMirrorComponent {
        return SmartMirrorApp
                .activityComponentBuilders[SignUpMirrorActivity::class.java]?.build() as SignUpMirrorComponent
    }

    override fun injectMembers(instance: SignUpMirrorComponent) {
        instance.injectMembers(this)
    }


    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_mirror)
        presenter.signInMirror()
    }

    /* callbacks */

    override fun onMirrorCreated(mirror: Mirror, mirrorSession: MirrorSession) {
        if (mirror.isWaitingForTuner) {
            qr_code_imageView.setImageBitmap(presenter.generateQrCode(mirrorSession.id))
            presenter.fetchIsTunerConnected(mirrorSession.id)
        } else {
            onTunerConnected()
        }
    }

    override fun onTunerConnected() {
        val intent = Intent(context, MirrorDashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}