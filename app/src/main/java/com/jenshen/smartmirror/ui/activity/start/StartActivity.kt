package com.jenshen.smartmirror.ui.activity.start

import android.content.Intent
import android.os.Bundle
import com.jenshen.compat.base.view.impl.BaseActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.ui.activity.signIn.SignInActivity
import com.jenshen.smartmirror.ui.activity.qrcode.QRCodeActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        mirror_button.setOnClickListener {
            startActivity(Intent(this, QRCodeActivity::class.java))
        }

        mirrorTuner_button.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}