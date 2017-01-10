package com.jenshen.smartmirror.ui.activity.edit.mirror

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.event.OrientationModeSettings
import com.jenshen.smartmirror.data.event.PrecipitationSettings
import com.jenshen.smartmirror.data.event.UserInfoSettings
import com.jenshen.smartmirror.data.model.mirror.EditMirrorModel
import com.jenshen.smartmirror.data.model.widget.WidgetConfigurationModel
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.di.component.activity.edit.mirror.EditMirrorComponent
import com.jenshen.smartmirror.ui.activity.choose.widget.ChooseWidgetActivity
import com.jenshen.smartmirror.ui.activity.settings.mirror.MirrorSettingsActivity
import com.jenshen.smartmirror.ui.mvp.presenter.edit.mirror.EditMirrorPresenter
import com.jenshen.smartmirror.ui.mvp.view.edit.mirror.EditMirrorView
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshen.smartmirror.util.widget.createWidget
import com.jenshensoft.widgetview.WidgetView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_edit_mirror.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode



class EditMirrorActivity : BaseDiMvpActivity<EditMirrorComponent, EditMirrorView, EditMirrorPresenter>(), EditMirrorView {

    companion object {
        private val EXTRA_MIRROR_KEY = "EXTRA_MIRROR_KEY"
        private val EXTRA_MIRROR_CONFIGURATION_KEY = "EXTRA_MIRROR_CONFIGURATION_KEY"
        private val MODEL_KEY = "MODEL_KEY"
        private val IS_SAVED_KEY = "IS_SAVED_KEY"

        fun start(context: Context, mirrorKey: String, configurationKey: String? = null) {
            RxPermissions.getInstance(context)
                    .request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CALENDAR)
                    .subscribe { granted ->
                        if (granted) {
                            val intent = Intent(context, EditMirrorActivity::class.java)
                            intent.putExtra(EXTRA_MIRROR_KEY, mirrorKey)
                            intent.putExtra(EXTRA_MIRROR_CONFIGURATION_KEY, configurationKey)
                            context.startActivity(intent)
                        } else {
                            AlertDialog.Builder(context)
                                    .setTitle(R.string.warning)
                                    .setMessage(R.string.error_location_permission)
                                    .setPositiveButton(R.string.ok, null)
                                    .show()
                        }
                    }
        }
    }

    private var editMirrorModel: EditMirrorModel? = null
    private var isSaved = true

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

        EventBus.getDefault().register(this)

        val mirrorId = intent.getStringExtra(EXTRA_MIRROR_KEY)
        val configurationKey = intent.getStringExtra(EXTRA_MIRROR_CONFIGURATION_KEY)
        if (configurationKey == null && editMirrorModel == null) {
            val view = LayoutInflater.from(context).inflate(R.layout.partial_dialog_edit_configuration, null)
            view.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

            val dialog = AlertDialog.Builder(context)
                    .setView(view)
                    .setPositiveButton(R.string.ok, null)
                    .setNegativeButton(R.string.cancel, { dialogInterface, i -> finish() })
                    .create()

            with(dialog) {
                setCancelable(false)
                show()
                getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    val nameEdit = view.findViewById(R.id.configurationName) as EditText
                    val title = nameEdit.text.toString()
                    val columnsEdit = view.findViewById(R.id.columnsCount) as EditText
                    val columns = columnsEdit.text.toString()
                    val rowsEdit = view.findViewById(R.id.rowsCount) as EditText
                    val rows = rowsEdit.text.toString()
                    if (title.isEmpty() || columns.isEmpty() || rows.isEmpty()) {
                        Toast.makeText(context, R.string.error_cant_be_empty, Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    onMirrorConfigurationLoaded(EditMirrorModel(mirrorId,
                            columns.toInt(),
                            rows.toInt(),
                            title))
                    dialog.dismiss()
                }
            }
        } else if (editMirrorModel == null) {
            presenter.loadMirrorConfiguration(configurationKey)
        }

        widgetContainer.setOnWidgetRemovedCallback { widgetView, widgetMotionInfo ->
            val deletedWidgetModel = editMirrorModel!!.widgets.find { widgetView.tag as WidgetKey == it.widgetKey }
            if (deletedWidgetModel!!.key == null) {
                editMirrorModel!!.widgets.remove(deletedWidgetModel)
            } else {
                deletedWidgetModel.isDeleted = true
            }
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (editMirrorModel != null) {
            updateModel(editMirrorModel!!)
        }
        outState.putParcelable(MODEL_KEY, editMirrorModel)
        outState.putBoolean(IS_SAVED_KEY, isSaved)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ChooseWidgetActivity.RESULT_KEY_CHOSE_WIDGET && resultCode != Activity.RESULT_OK && data == null) {
            return
        }
        val widgetModel = data!!.getParcelableExtra<WidgetConfigurationModel>(ChooseWidgetActivity.RESULT_EXTRA_WIDGET)

        val widgetView = createWidgetView(widgetModel)
        editMirrorModel!!.widgets.add(widgetModel)
        widgetContainer.addWidgetView(widgetView)
    }

    override fun onBackPressed() {
        if (!isSaved) {
            val dialog = AlertDialog.Builder(context)
                    .setTitle(R.string.warning)
                    .setMessage(R.string.error_not_save)
                    .setNeutralButton(R.string.dialog_save, { dialogInterface: DialogInterface, i: Int ->
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

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val menuItem = menu.getItem(2)
        menuItem.isVisible = editMirrorModel != null && editMirrorModel!!.configurationKey != null
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addWidget_item_menu -> {
                ChooseWidgetActivity.startForResult(this)
                return true
            }
            R.id.save_item_menu -> {
                saveConfiguration()
                return true
            }
            R.id.configurationSettings_item_menu -> {
                MirrorSettingsActivity.start(context, editMirrorModel!!.configurationKey!!)
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /* callbacks */

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPrecipitationMessageEvent(event: PrecipitationSettings) {
        editMirrorModel!!.isEnablePrecipitation = event.isEnable
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserInfoMessageEvent(event: UserInfoSettings) {
        editMirrorModel!!.userInfoKey = event.userInfoKey
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSetOrientationMessageEvent(event: OrientationModeSettings) {
        editMirrorModel!!.orientationMode = event.orientationMode
    }

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
        invalidateOptionsMenu()
    }

    override fun onMirrorConfigurationLoaded(model: EditMirrorModel) {
        presenter.clearWidgetsUpdaters()
        editMirrorModel = model
        widgetContainer.setColumnCount(model.columnsCount)
        widgetContainer.setRowCount(model.rowsCount)
        widgetContainer.requestLayout()
        editMirrorModel?.widgets
                ?.filter { !it.isDeleted }
                ?.forEach { updateWidgetPosition(createWidgetView(it), it) }
        invalidateOptionsMenu()
    }

    override fun onWidgetUpdate(infoData: WidgetData) {
        widgetContainer.widgets
                .find {
                    val widgetKey = it.tag as WidgetKey
                    (widgetKey.key == infoData.widgetKey.key) && widgetKey.number == infoData.widgetKey.number
                }
                ?.apply { presenter.updateWidget(infoData, getChildAt(0) as Widget<*>) }
    }

    /* private methods */

    private fun saveConfiguration() {
        if (editMirrorModel != null) {
            updateModel(editMirrorModel!!)
        }
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

    private fun updateModel(editMirrorModel: EditMirrorModel) {
        editMirrorModel.widgets
                .filter { !it.isDeleted }
                .forEach { widgetModel ->
                    val view = widgetContainer.widgets
                            .find {
                                val widgetKey = it.tag as WidgetKey
                                (widgetKey.key == widgetModel.widgetKey.key) && widgetKey.number == widgetModel.widgetKey.number
                            }
                    if (isSaved && widgetModel.widgetPosition != null) {
                        isSaved = widgetModel.widgetPosition == view!!.widgetPosition
                    }
                    widgetModel.widgetPosition = view!!.widgetPosition
                }
    }

    private fun isModelCompleted(editMirrorModel: EditMirrorModel): Boolean {
        var completed = true
        editMirrorModel.widgets
                .filter { !it.isDeleted }
                .forEach { completed = completed && !(it.widgetPosition?.isEmpty ?: true) }
        return completed
    }

    private fun restoreExtra(savedInstanceState: Bundle?) {
        isSaved = savedInstanceState?.getBoolean(IS_SAVED_KEY) ?: false
        val model = savedInstanceState?.getParcelable<EditMirrorModel>(MODEL_KEY)
        if (model != null) {
            onMirrorConfigurationLoaded(model)
        }
    }

    private fun createWidgetView(widgetModel: WidgetConfigurationModel): WidgetView {
        widgetModel.widgetKey.number = widgetContainer.widgets.filter { (it.tag as WidgetKey).key == widgetModel.widgetKey.key }.size
        presenter.addWidgetUpdater(widgetModel.widgetKey)
        val widget = createWidget(widgetModel.widgetKey.key, widgetModel.widgetInfo.defaultSize, context)
        widget.tag = widgetModel.widgetKey
        return widget
    }

    private fun updateWidgetPosition(widgetView: WidgetView, widgetModel: WidgetConfigurationModel) {
        with(widgetView.widgetPosition) {
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
        widgetContainer.addWidgetView(widgetView)
    }
}