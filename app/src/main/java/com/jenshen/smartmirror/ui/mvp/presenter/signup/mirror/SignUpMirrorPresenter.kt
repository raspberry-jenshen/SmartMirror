package com.jenshen.smartmirror.ui.mvp.presenter.signup.mirror

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.data.firebase.model.mirror.Mirror
import com.jenshen.smartmirror.interactor.firebase.api.ApiInteractor
import com.jenshen.smartmirror.interactor.firebase.auth.FirebaseAuthInteractor
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.ui.mvp.view.signup.mirror.SignUpMirrorView
import com.jenshen.smartmirror.util.reactive.applyProgress
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.Single
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class SignUpMirrorPresenter @Inject constructor(private val authInteractor: FirebaseAuthInteractor,
                                                private val apiInteractor: ApiInteractor,
                                                private val preferencesManager: PreferencesManager) : MvpRxPresenter<SignUpMirrorView>() {

    private var isTaskFinished = true

    override fun attachView(view: SignUpMirrorView?) {
        super.attachView(view)
        authInteractor.fetchAuth()
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ createMirrorAccount() }, { view?.handleError(it) })
    }

    fun fetchIsTunerConnected(id: String) {
        compositeDisposable.add(apiInteractor.isTunerConnected(id)
                .applySchedulers(Schedulers.io())
                .subscribe({
                    view?.onTunerConnected()
                }, {
                    view?.handleError(it)
                }))
    }

    fun signInMirror() {
        authInteractor.signInMirror()
                .applySchedulers(Schedulers.io())
                .applyProgress(Consumer {
                    view?.showProgress()
                    isTaskFinished = false
                }, Action {
                    if (isTaskFinished) {
                        view?.hideProgress()
                    }
                })
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({}, {
                    view?.handleError(it)
                    view?.hideProgress()
                    isTaskFinished = true
                })
    }

    private fun createMirrorAccount() {
        Single.fromCallable { preferencesManager.getSession()!! }
                .cast(MirrorSession::class.java)
                .flatMap { session ->
                    apiInteractor.createOrGetMirror(session)
                            .map { MirrorInfo(it, session) }
                }
                .doOnSuccess {
                    if (it.mirror.isWaitingForTuner) {
                        it.bitmap = generateQrCode(it.mirrorSession.key)
                    }
                }
                .applySchedulers(Schedulers.io())
                .applyProgress(Consumer {
                    if (isTaskFinished) {
                        view?.showProgress()
                        isTaskFinished = false
                    }
                }, Action {
                    view?.hideProgress()
                    isTaskFinished = true
                })
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({
                    view?.onMirrorCreated(it.mirror, it.mirrorSession, it.bitmap)
                }, {
                    view?.handleError(it)
                })
    }

    @Throws(WriterException::class)
    private fun generateQrCode(myCodeText: String): Bitmap {
        val hintMap = Hashtable<EncodeHintType, ErrorCorrectionLevel>()
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H) // H = 30% damage

        val qrCodeWriter = QRCodeWriter()

        val size = 1024

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

    private data class MirrorInfo(var mirror: Mirror, var mirrorSession: MirrorSession) {
        var bitmap: Bitmap? = null
    }
}