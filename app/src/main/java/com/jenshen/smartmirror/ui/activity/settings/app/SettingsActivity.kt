package com.jenshen.smartmirror.ui.activity.settings.app

import android.os.Bundle
import com.jenshen.compat.base.view.impl.BaseActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.ui.fragment.settings.app.SettingsFragment
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener({ v -> finish() })
            actionBar.setTitle(R.string.settings)
        }

        if (savedInstanceState == null) {
            val preferenceFragment = SettingsFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, preferenceFragment)
                    .commit()
        }
    }
}