package com.jenshen.smartmirror.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.module.activity.start.service.StartMirrorServiceModule
import com.jenshen.smartmirror.ui.activity.dashboard.mirror.MirrorDashboardActivity
import com.jenshen.smartmirror.ui.mvp.presenter.start.service.StartMirrorServicePresenter
import com.jenshen.smartmirror.ui.mvp.view.start.service.StartMirrorServiceView
import javax.inject.Inject


class StartMirrorService : Service(), StartMirrorServiceView {

    @Inject
    protected lateinit var presenter: StartMirrorServicePresenter

    /* lifecycle */

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        presenter.isSessionExist()
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        SmartMirrorApp.rootComponent
                .plusMirrorService(StartMirrorServiceModule())
                .injectMembers(this)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(false)
    }

    /* callbacks */

    override fun openMirrorScreen() {
        val intentActivity = Intent(context, MirrorDashboardActivity::class.java)
        intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intentActivity)
        stopSelf()
    }

    /* base */

    override fun handleError(throwable: Throwable?) {
        Log.e(context.getString(R.string.app_name), throwable.toString())
    }

    override fun getContext() = this

    override fun hideProgress() {
        throw UnsupportedOperationException("not implemented")
    }

    override fun showProgress() {
        throw UnsupportedOperationException("not implemented")
    }
}
