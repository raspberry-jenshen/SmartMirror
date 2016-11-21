package com.jenshen.smartmirror.ui.activity.dashboard.tuner

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.component.activity.dashboard.tuner.TunerDashboardComponent
import com.jenshen.smartmirror.ui.activity.qrScan.QRCodeScanActivity
import com.jenshen.smartmirror.ui.activity.settings.SettingsActivity
import com.jenshen.smartmirror.ui.mvp.presenter.dashboard.tuner.TunerDashboardPresenter
import com.jenshen.smartmirror.ui.mvp.view.dashboard.tuner.TunerDashboardView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_dashboard_tuner.*




class TunerDashboardActivity : BaseDiMvpActivity<TunerDashboardComponent, TunerDashboardView, TunerDashboardPresenter>(), TunerDashboardView {

    /* inject */

    override fun createComponent(): TunerDashboardComponent {
        return SmartMirrorApp
                .sessionActivityComponentBuilders!![TunerDashboardActivity::class.java]!!
                .build() as TunerDashboardComponent
    }

    override fun injectMembers(instance: TunerDashboardComponent) {
        instance.injectMembers(this)
    }

    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_tuner)
        setSupportActionBar(toolbar)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == QRCodeScanActivity.RESULT_KEY_QR_CODE && resultCode != Activity.RESULT_OK && data == null) {
            return
        }
        presenter.subscribeOnMirror(data!!.getStringExtra(QRCodeScanActivity.RESULT_EXTRA_MIRROR_ID))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_tuner_dashboard, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_item_menu -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.scan_QrCode_item_menu -> {
                RxPermissions.getInstance(this)
                        .request(Manifest.permission.CAMERA)
                        .subscribe { granted ->
                            if (granted) {
                                val intent = Intent(this, QRCodeScanActivity::class.java)
                                startActivityForResult(intent, QRCodeScanActivity.RESULT_KEY_QR_CODE)
                            } else {
                                AlertDialog.Builder(this)
                                        .setTitle(R.string.warning)
                                        .setMessage(R.string.error_camera_permission)
                                        .setPositiveButton(R.string.ok, null)
                                        .show()
                            }
                        }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
