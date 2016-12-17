package com.jenshen.smartmirror.ui.activity.settings.mirror

import android.os.Bundle
import android.view.MenuItem
import com.jenshen.compat.base.view.impl.BaseActivity
import com.jenshen.smartmirror.R
import kotlinx.android.synthetic.main.activity_mirror_settings.*

class MirrorSettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mirror_settings)
        setupToolbar()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
    }
}
