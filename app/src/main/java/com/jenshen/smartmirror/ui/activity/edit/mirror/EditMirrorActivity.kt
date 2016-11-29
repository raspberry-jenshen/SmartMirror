package com.jenshen.smartmirror.ui.activity.edit.mirror

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.component.activity.edit.mirror.EditMirrorComponent
import com.jenshen.smartmirror.ui.activity.choose.widget.ChooseWidgetActivity
import com.jenshen.smartmirror.ui.mvp.presenter.edit.mirror.EditMirrorPresenter
import com.jenshen.smartmirror.ui.mvp.view.edit.mirror.EditMirrorView
import kotlinx.android.synthetic.main.activity_qr_scanner.*

class EditMirrorActivity : BaseDiMvpActivity<EditMirrorComponent, EditMirrorView, EditMirrorPresenter>(), EditMirrorView {

    companion object {
        val EXTRA_MIRROR_CONFIGURATION_ID = "EXTRA_MIRROR_ID"
    }

    private lateinit var configurationModel: ConfigurationModel

    /* inject */

    override fun createComponent(): EditMirrorComponent {
        return SmartMirrorApp
                .sessionActivityComponentBuilders!![EditMirrorActivity::class.java]!!
                .build() as EditMirrorComponent
    }

    override fun injectMembers(instance: EditMirrorComponent) {
        instance.injectMembers(this)
    }


    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_mirror)
        setupToolbar()
        val stringExtra = intent.getStringExtra(EXTRA_MIRROR_CONFIGURATION_ID)
        if (stringExtra == null) {
            val editText = EditText(context)
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            editText.layoutParams = layoutParams

            val dialog = AlertDialog.Builder(context)
                    .setTitle(R.string.editMirror_typeTitle)
                    .setView(editText)
                    .setPositiveButton(R.string.ok, null)
                    .create()

            /* with(dialog) {
                 setCancelable(false)
                 show()
                 getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                     val title = editText.text.toString()
                     if (!title.isEmpty()) {
                         configurationModel = ConfigurationModel(title)
                         dialog.dismiss()
                     } else {
                         Toast.makeText(context, R.string.error_cant_be_empty, Toast.LENGTH_LONG).show()
                     }
                 }
             }*/
        } else {
            //todo load mirror
        }
    }

    /* menu */

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit_mirror, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addWidget_item_menu -> {
                val intent = Intent(this, ChooseWidgetActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    class ConfigurationModel(val title: String) {

    }

    /* private methods */

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
    }
}