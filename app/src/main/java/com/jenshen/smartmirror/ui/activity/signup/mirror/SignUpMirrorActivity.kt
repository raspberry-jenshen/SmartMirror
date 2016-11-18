package com.jenshen.smartmirror.ui.activity.signup.mirror

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
import com.jenshen.smartmirror.data.firebase.Mirror
import com.jenshen.smartmirror.di.component.activity.signUp.mirror.SignUpMirrorComponent
import com.jenshen.smartmirror.ui.mvp.presenter.signup.mirror.SignUpMirrorPresenter
import com.jenshen.smartmirror.ui.mvp.view.signup.mirror.SignUpMirrorView
import kotlinx.android.synthetic.main.activity_qr_code.*
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
        setContentView(R.layout.activity_qr_code)
        presenter.signInMirror()
    }

    /* callbacks */

    override fun onMirrorCreated(mirror: Mirror) {
        qr_code_imageView.setImageBitmap(generateQrCode("test test test"))
    }

    @Throws(WriterException::class)
    fun generateQrCode(myCodeText: String): Bitmap {
        val hintMap = Hashtable<EncodeHintType, ErrorCorrectionLevel>()
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H) // H = 30% damage

        val qrCodeWriter = QRCodeWriter()

        val size = 256

        val bitMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap)
        val width = bitMatrix.width
        val bmp = Bitmap.createBitmap(width, width, Bitmap.Config.RGB_565)
        for (x in 0..width - 1) {
            for (y in 0..width - 1) {
                bmp.setPixel(y, x, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bmp
    }
}