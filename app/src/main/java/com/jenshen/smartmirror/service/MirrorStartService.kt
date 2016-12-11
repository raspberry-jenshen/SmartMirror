package com.jenshen.smartmirror.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.ui.activity.dashboard.mirror.MirrorDashboardActivity
import com.jenshen.smartmirror.ui.mvp.presenter.start.service.StartMirrorServisePresenter
import com.jenshen.smartmirror.ui.mvp.view.start.service.StartMirrorServiceView


class MirrorStartService : Service(), StartMirrorServiceView {

    private lateinit var presenter: StartMirrorServisePresenter

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
        SmartMirrorApp.rootComponent.injectService(this)
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
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgress() {
        throw UnsupportedOperationException("not implemented")
    }
}
