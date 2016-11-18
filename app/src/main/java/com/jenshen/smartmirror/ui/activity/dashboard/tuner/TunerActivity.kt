package com.jenshen.smartmirror.ui.activity.dashboard.tuner

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.ui.activity.qrScan.QRCodeScanActivity
import com.jenshen.smartmirror.ui.activity.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_tuner.*

class TunerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tuner)
        setSupportActionBar(toolbar)
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
                val intent = Intent(this, QRCodeScanActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
