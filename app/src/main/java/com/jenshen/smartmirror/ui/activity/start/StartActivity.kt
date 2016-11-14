package com.jenshen.smartmirror.ui.activity.start

import android.content.Intent
import android.os.Bundle
import com.jenshen.compat.base.view.impl.BaseActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.ui.activity.SignInActivity
import com.jenshen.smartmirror.ui.activity.qrcode.MirrorActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        mirror_button.setOnClickListener {
            startActivity(Intent(this, MirrorActivity::class.java))
        }

        mirrorTuner_button.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}