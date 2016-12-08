package com.jenshen.smartmirror.ui.activity.qrScan

import android.app.Activity
import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.Menu
import android.view.MenuItem
import com.google.zxing.Result
import com.jenshen.compat.base.view.impl.BaseActivity
import com.jenshen.smartmirror.R
import kotlinx.android.synthetic.main.activity_qr_scanner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView




class QRCodeScanActivity : BaseActivity(), ZXingScannerView.ResultHandler {

    companion object {
        val RESULT_KEY_QR_CODE = 78
        val RESULT_EXTRA_MIRROR_ID = "RESULT_EXTRA_MIRROR_ID"
        private val FLASH_STATE = "FLASH_STATE"
        private val CAMERA_ID = "CAMERA_ID"
    }

    private var flash: Boolean = false
    private var cameraId = 0

    private lateinit var scannerView: ZXingScannerView

    /* lifecycle */

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_qr_scanner)
        if (state != null) {
            flash = state.getBoolean(FLASH_STATE, false)
            cameraId = state.getInt(CAMERA_ID, -1)
        } else {
            flash = false
            cameraId = 0
        }
        setupToolbar()

        scannerView = ZXingScannerView(this)
        content_frame.addView(scannerView)   // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera(cameraId)
        scannerView.flash = flash
        scannerView.setAutoFocus(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(FLASH_STATE, flash)
        outState.putInt(CAMERA_ID, cameraId)
    }

    public override fun onPause() {
        super.onPause()
        scannerView.stopCamera()           // Stop camera on pause
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_qr_code_scanner, menu)
        val switchCameraItem = menu.findItem(R.id.switch_camera_item_menu)
        setIconCamera(switchCameraItem, cameraId)
        val switchFlashItem = menu.findItem(R.id.turn_flashLight_item_menu)
        setIconFlash(switchFlashItem, flash)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.switch_camera_item_menu -> {
                val numberOfCameras = Camera.getNumberOfCameras()
                val oldCameraId: Int = cameraId
                (0..numberOfCameras - 1)
                        .asSequence()
                        .filter { it == oldCameraId }
                        .map {
                            var id = it + 1
                            if (id > numberOfCameras - 1) {
                                id = 0
                            }
                            return@map id
                        }
                        .forEach {
                            cameraId = it
                            setIconCamera(item, it)
                            switchCamera(it)
                        }
                return true
            }
            R.id.turn_flashLight_item_menu -> {
                flash = !flash
                setIconFlash(item, flash)
                scannerView.flash = flash
                return true
            }
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this);
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    /* callbacks */

    override fun handleResult(rawResult: Result) {
        scannerView.resumeCameraPreview(this)
        val intent = Intent()
        intent.putExtra(RESULT_EXTRA_MIRROR_ID, rawResult.text.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    /* private methods */

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
    }

    private fun switchCamera(cameraId: Int) {
        scannerView.startCamera(cameraId)
        scannerView.flash = flash
        scannerView.setAutoFocus(true)
    }

    private fun setIconFlash(menuItem: MenuItem, flash: Boolean) {
        if (flash) {
            menuItem.setIcon(R.drawable.ic_flash_off)
        } else {
            menuItem.setIcon(R.drawable.ic_flash_on)
        }
    }

    @Suppress("DEPRECATION")
    private fun setIconCamera(menuItem: MenuItem, cameraId: Int) {
        val info = Camera.CameraInfo()
        Camera.getCameraInfo(cameraId, info)
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            menuItem.setIcon(R.drawable.ic_camera_back)
        } else if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            menuItem.setIcon(R.drawable.ic_camera_front)
        } else {
            menuItem.setIcon(R.drawable.ic_camera_front)
        }
    }
}