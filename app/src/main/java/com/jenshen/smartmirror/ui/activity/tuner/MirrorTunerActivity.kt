package com.jenshen.smartmirror.ui.activity.tuner

import android.os.Bundle
import com.jenshen.compat.base.view.impl.BaseActivity
import kotlinx.android.synthetic.main.activity_start.*

class MirrorTunerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mirror_button.setOnClickListener {
            //startActivity(Intent(context, MirrorActivity::class.java))
        }
    }
}