package com.jenshen.smartmirror.ui.activity.add.widget

import android.os.Bundle
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.component.activity.add.widget.AddWidgetComponent
import com.jenshen.smartmirror.ui.mvp.presenter.add.widget.AddWidgetPresenter
import com.jenshen.smartmirror.ui.mvp.view.add.widget.AddWidgetView
import kotlinx.android.synthetic.main.activity_add_widget.*

class AddWidgetActivity : BaseDiMvpActivity<AddWidgetComponent, AddWidgetView, AddWidgetPresenter>(), AddWidgetView {

    /* inject */

    override fun createComponent(): AddWidgetComponent {
        return SmartMirrorApp
                .sessionActivityComponentBuilders!![AddWidgetActivity::class.java]!!
                .build() as AddWidgetComponent
    }

    override fun injectMembers(instance: AddWidgetComponent) {
        instance.injectMembers(this)
    }


    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_widget)
        setupToolbar()
        addWidget_button.setOnClickListener {
            presenter.createWidget(
                    nameEdit.text.toString(),
                    sizeWidth.text.toString(),
                    sizeHeight.text.toString())
        }
    }

    /* private methods */

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
    }
}