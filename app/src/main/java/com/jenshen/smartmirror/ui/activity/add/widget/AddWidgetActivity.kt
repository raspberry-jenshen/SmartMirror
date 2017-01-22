package com.jenshen.smartmirror.ui.activity.add.widget

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.component.activity.add.widget.AddWidgetComponent
import com.jenshen.smartmirror.ui.mvp.presenter.add.widget.AddWidgetPresenter
import com.jenshen.smartmirror.ui.mvp.view.add.widget.AddWidgetView
import kotlinx.android.synthetic.main.activity_add_widget.*
import kotlinx.android.synthetic.main.partial_toolbar.*


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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this);
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /* private methods */

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
    }
}