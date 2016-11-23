package com.jenshen.smartmirror.ui.activity.edit.mirror

import android.os.Bundle
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.component.activity.edit.mirror.EditMirrorComponent
import com.jenshen.smartmirror.di.component.activity.signIn.SignInComponent
import com.jenshen.smartmirror.ui.activity.signIn.SignInTunerActivity
import com.jenshen.smartmirror.ui.mvp.presenter.edit.mirror.EditMirrorPresenter
import com.jenshen.smartmirror.ui.mvp.view.edit.mirror.EditMirrorView

class EditMirrorActivity : BaseDiMvpActivity<EditMirrorComponent, EditMirrorView, EditMirrorPresenter>(), EditMirrorView {

    companion object {
        val EXTRA_MIRROR_CONFIGURATION_ID = "EXTRA_MIRROR_ID"
    }

    /* inject */

    override fun createComponent(): EditMirrorComponent {
        return SmartMirrorApp
                .activityComponentBuilders[ EditMirrorActivity::class.java]?.build() as EditMirrorComponent
    }

    override fun injectMembers(instance:  EditMirrorComponent) {
        instance.injectMembers(this)
    }


    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_mirror)
        val stringExtra = intent.getStringExtra(EXTRA_MIRROR_CONFIGURATION_ID)
        if (stringExtra == null) {

        } else {

        }
    }

}