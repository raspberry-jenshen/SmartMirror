package com.jenshen.smartmirror.ui.activity.qrScan

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.zxing.Result
import com.jenshen.compat.base.view.impl.BaseActivity
import com.jenshen.smartmirror.R
import kotlinx.android.synthetic.main.activity_qr_scanner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class QRCodeScanActivity : BaseActivity(), ZXingScannerView.ResultHandler {

    companion object {
        private val FLASH_STATE = "FLASH_STATE"
        private val AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE"
        private val CAMERA_ID = "CAMERA_ID"
    }

    private var mFlash: Boolean = false
    private var mAutoFocus: Boolean = false
    private var mCameraId = -1

    private lateinit var scannerView: ZXingScannerView

    /* lifecycle */

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_qr_scanner)
        if (state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false)
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true)
            mCameraId = state.getInt(CAMERA_ID, -1)
        } else {
            mFlash = false
            mAutoFocus = true
            mCameraId = -1
        }
        setupToolbar()

        scannerView = ZXingScannerView(this)
        content_frame.addView(scannerView)   // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera(mCameraId)
        scannerView.flash = mFlash
        scannerView.setAutoFocus(mAutoFocus)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(FLASH_STATE, mFlash)
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus)
        outState.putInt(CAMERA_ID, mCameraId)
    }

    public override fun onPause() {
        super.onPause()
        scannerView.stopCamera()           // Stop camera on pause
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_qr_code_scanner, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.switch_camera_item_menu -> {

                return true
            }
            R.id.turn_flashLight_item_menu -> {
                mFlash = !mFlash
                if (mFlash) {
                    item.setIcon(R.drawable.ic_flash_on)
                } else {
                    item.setIcon(R.drawable.ic_flash_off)
                }
                scannerView.flash = mFlash
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun handleResult(rawResult: Result) {
        // Do something with the result here
        //Log.v(FragmentActivity.TAG, rawResult.text) // Prints scan results
        //Log.v(FragmentActivity.TAG, rawResult.barcodeFormat.toString()) // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        scannerView.resumeCameraPreview(this)
    }

    /* private methods */

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun switchCamera(cameraId : Int) {
        mCameraId = cameraId
        scannerView.startCamera(mCameraId)
        scannerView.flash = mFlash
        scannerView.setAutoFocus(mAutoFocus)
    }
}