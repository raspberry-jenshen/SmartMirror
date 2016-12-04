package com.jenshen.smartmirror.ui.activity.edit.mirror

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.model.EditMirrorModel
import com.jenshen.smartmirror.data.model.WidgetModel
import com.jenshen.smartmirror.di.component.activity.edit.mirror.EditMirrorComponent
import com.jenshen.smartmirror.ui.activity.choose.widget.ChooseWidgetActivity
import com.jenshen.smartmirror.ui.mvp.presenter.edit.mirror.EditMirrorPresenter
import com.jenshen.smartmirror.ui.mvp.view.edit.mirror.EditMirrorView
import com.jenshen.smartmirror.util.widget.getViewForWidget
import com.jenshensoft.widgetview.WidgetView
import kotlinx.android.synthetic.main.activity_edit_mirror.*

class EditMirrorActivity : BaseDiMvpActivity<EditMirrorComponent, EditMirrorView, EditMirrorPresenter>(), EditMirrorView {

    companion object {
        val EXTRA_MIRROR_CONFIGURATION_ID = "EXTRA_MIRROR_ID"
        private val MODEL_KEY = "MODEL_KEY"
        private val IS_SAVED_KEY = "IS_SAVED_KEY"
    }

    private var editMirrorModel: EditMirrorModel? = null
    private var isSaved = false

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
        restoreExtra(savedInstanceState)
        val stringExtra = intent.getStringExtra(EXTRA_MIRROR_CONFIGURATION_ID)
        if (stringExtra == null && editMirrorModel == null) {
            val editText = EditText(context)
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            editText.layoutParams = layoutParams

            val dialog = AlertDialog.Builder(context)
                    .setTitle(R.string.editMirror_typeTitle)
                    .setView(editText)
                    .setPositiveButton(R.string.ok, null)
                    .create()

            with(dialog) {
                setCancelable(false)
                show()
                getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    val title = editText.text.toString()
                    if (!title.isEmpty()) {
                        editMirrorModel = EditMirrorModel(title)
                        dialog.dismiss()
                    } else {
                        Toast.makeText(context, R.string.error_cant_be_empty, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else if (editMirrorModel == null) {
            //todo load mirror
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        updateModel()
        outState.putParcelable(MODEL_KEY, editMirrorModel)
        outState.putBoolean(IS_SAVED_KEY, isSaved)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ChooseWidgetActivity.RESULT_KEY_CHOSE_WIDGET && resultCode != Activity.RESULT_OK && data == null) {
            return
        }
        val widgetModel = data!!.getParcelableExtra<WidgetModel>(ChooseWidgetActivity.RESULT_EXTRA_WIDGET)
        val sameWidgetsCount = editMirrorModel?.list?.filter { it.key == widgetModel.key }?.size ?: 0
        widgetModel.tag += sameWidgetsCount
        val widget = createWidget(widgetModel)
        editMirrorModel!!.list.add(widgetModel)
        widgetContainer.addWidgetView(widget)
    }

    override fun onBackPressed() {
        if (!isSaved) {
            val dialog = AlertDialog.Builder(context)
                    .setTitle(R.string.warning)
                    .setMessage(R.string.error_not_save)
                    .setNeutralButton(R.string.dialog_save_and_exit, { dialogInterface: DialogInterface, i: Int ->
                        presenter.saveConfiguration(editMirrorModel!!)
                    })
                    .setNegativeButton(R.string.dialog_no, null)
                    .setPositiveButton(R.string.dialog_yes, { dialogInterface: DialogInterface, i: Int ->
                        NavUtils.navigateUpFromSameTask(this)
                    })
                    .create()
            dialog.setCancelable(false)
            dialog.show()
        } else {
            NavUtils.navigateUpFromSameTask(this)
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
                startActivityForResult(intent, ChooseWidgetActivity.RESULT_KEY_CHOSE_WIDGET)
                return true
            }
            R.id.save_item_menu -> {
                presenter.saveConfiguration(editMirrorModel!!)
                return true
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /* callbacks */

    fun onSavedConfiguration() {
        isSaved = true
    }

    /* private methods */

    private fun saveConfiguration(){
        updateModel()

    }

    private fun createWidget(widgetModel: WidgetModel): WidgetView {
        val view = getViewForWidget(widgetModel, context)
        val widgetView = WidgetView(context)
        widgetView.addView(view)
        widgetView.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        widgetView.tag = widgetModel.tag
        return widgetView
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
    }

    private fun updateModel() {
        editMirrorModel?.list?.forEach { widgetModel ->
            val view = widgetContainer.widgets.find { view -> widgetModel.tag =3= view.tag }
            if (isSaved && widgetModel.widgetPosition != null) {
                isSaved = widgetModel.widgetPosition == view!!.widgetPosition
            }
            widgetModel.widgetPosition = view!!.widgetPosition
        }
    }

    private fun isModelCompleted(editMirrorModel: EditMirrorModel) {
        var completed = true
        editMirrorModel.list.
    }

    private fun restoreExtra(savedInstanceState: Bundle?) {
        isSaved = savedInstanceState?.getBoolean(IS_SAVED_KEY) ?: false
        editMirrorModel = savedInstanceState?.getParcelable<EditMirrorModel>(MODEL_KEY)
        editMirrorModel?.list?.forEach { widgetModel ->
            val widget = createWidget(widgetModel)
            with(widget.widgetPosition) {
                val widgetPosition = widgetModel.widgetPosition

                topLeftColumnLine = widgetPosition!!.topLeftColumnLine
                topLeftRowLine = widgetPosition.topLeftRowLine

                topRightColumnLine = widgetPosition.topRightColumnLine
                topRightRowLine = widgetPosition.topRightRowLine

                bottomLeftColumnLine = widgetPosition.bottomLeftColumnLine
                bottomLeftRowLine = widgetPosition.bottomLeftRowLine

                bottomRightColumnLine = widgetPosition.bottomRightColumnLine
                bottomRightRowLine = widgetPosition.bottomRightRowLine
            }
            widgetContainer.addWidgetView(widget)
        }
    }
}