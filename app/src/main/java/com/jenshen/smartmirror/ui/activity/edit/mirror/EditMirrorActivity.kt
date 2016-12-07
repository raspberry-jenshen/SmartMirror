package com.jenshen.smartmirror.ui.activity.edit.mirror

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
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
import com.jenshen.smartmirror.util.widget.createWidget
import kotlinx.android.synthetic.main.activity_edit_mirror.*

class EditMirrorActivity : BaseDiMvpActivity<EditMirrorComponent, EditMirrorView, EditMirrorPresenter>(), EditMirrorView {

    companion object {
        private val EXTRA_MIRROR_KEY = "EXTRA_MIRROR_KEY"
        private val EXTRA_MIRROR_CONFIGURATION_KEY = "EXTRA_MIRROR_CONFIGURATION_KEY"
        private val MODEL_KEY = "MODEL_KEY"
        private val IS_SAVED_KEY = "IS_SAVED_KEY"

        fun start(context: Context, mirrorKey: String, configurationKey: String? = null) {
            val intent = Intent(context, EditMirrorActivity::class.java)
            intent.putExtra(EXTRA_MIRROR_KEY, mirrorKey)
            intent.putExtra(EXTRA_MIRROR_CONFIGURATION_KEY, configurationKey)
            context.startActivity(intent)
        }
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
        val mirrorId = intent.getStringExtra(EXTRA_MIRROR_KEY)
        val configurationKey = intent.getStringExtra(EXTRA_MIRROR_CONFIGURATION_KEY)
        if (configurationKey == null && editMirrorModel == null) {
            val editText = EditText(context)
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            editText.layoutParams = layoutParams

            val dialog = AlertDialog.Builder(context)
                    .setTitle(R.string.editMirror_typeTitle)
                    .setView(editText)
                    .setPositiveButton(R.string.ok, null)
                    .setNegativeButton(R.string.cancel, { dialogInterface, i -> finish() })
                    .create()

            with(dialog) {
                setCancelable(false)
                show()
                getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    val title = editText.text.toString()
                    if (!title.isEmpty()) {
                        editMirrorModel = EditMirrorModel(mirrorId, title)
                        dialog.dismiss()
                    } else {
                        Toast.makeText(context, R.string.error_cant_be_empty, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else if (editMirrorModel == null) {
            presenter.loadMirrorConfiguration(configurationKey)
        } else {
            throw RuntimeException("Something went wrong")
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
        val widget = createWidget(widgetModel.widgetKey, context)
        widget.tag = widgetModel.tag
        editMirrorModel!!.list.add(widgetModel)
        widgetContainer.addWidgetView(widget)
    }

    override fun onBackPressed() {
        if (!isSaved) {
            val dialog = AlertDialog.Builder(context)
                    .setTitle(R.string.warning)
                    .setMessage(R.string.error_not_save)
                    .setNeutralButton(R.string.dialog_save_and_exit, { dialogInterface: DialogInterface, i: Int ->
                        saveConfiguration()
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
                saveConfiguration()
                return true
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /* callbacks */

    override fun onSavedConfiguration() {
        isSaved = true
        AlertDialog.Builder(context)
                .setTitle(R.string.dialog_completed)
                .setMessage(R.string.dialog_data_were_set)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.dialog_exit, { dialogInterface: DialogInterface, i: Int ->
                    NavUtils.navigateUpFromSameTask(this)
                })
                .create()
                .show()
    }

    override fun onMirrorConfigurationLoaded(model: EditMirrorModel) {
        editMirrorModel = model
    }

    /* private methods */

    private fun saveConfiguration() {
        updateModel()
        if (editMirrorModel != null && isModelCompleted(editMirrorModel!!)) {
            presenter.saveConfiguration(editMirrorModel!!)
        } else {
            AlertDialog.Builder(context)
                    .setTitle(R.string.warning)
                    .setMessage(R.string.error_finish_editing)
                    .setPositiveButton(R.string.ok, null)
                    .create()
                    .show()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
    }

    private fun updateModel() {
        editMirrorModel?.list?.forEach { widgetModel ->
            val view = widgetContainer.widgets
                    .find { widgetModel.tag == it.tag }
            if (isSaved && widgetModel.widgetPosition != null) {
                isSaved = widgetModel.widgetPosition == view!!.widgetPosition
            }
            widgetModel.widgetPosition = view!!.widgetPosition
        }
    }

    private fun isModelCompleted(editMirrorModel: EditMirrorModel): Boolean {
        var completed = true
        editMirrorModel.list.forEach {
            completed = completed && !(it.widgetPosition?.isEmpty ?: true)
        }
        return completed
    }

    private fun restoreExtra(savedInstanceState: Bundle?) {
        isSaved = savedInstanceState?.getBoolean(IS_SAVED_KEY) ?: false
        editMirrorModel = savedInstanceState?.getParcelable<EditMirrorModel>(MODEL_KEY)
        editMirrorModel?.list?.forEach { widgetModel ->
            val widget = createWidget(widgetModel.widgetKey, context)
            widget.tag = widgetModel.tag
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