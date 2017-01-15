package com.jenshen.smartmirror.ui.activity.choose.widget

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.jenshen.compat.base.view.impl.mvp.lce.component.lce.BaseDiLceMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetConfigurationModel
import com.jenshen.smartmirror.data.model.widget.WidgetModel
import com.jenshen.smartmirror.di.component.activity.choose.widget.ChooseWidgetComponent
import com.jenshen.smartmirror.ui.adapter.widgets.WidgetsAdapter
import com.jenshen.smartmirror.ui.mvp.presenter.choose.widget.ChooseWidgetPresenter
import com.jenshen.smartmirror.ui.mvp.view.choose.widget.ChooseWidgetView
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.partial_toolbar.*

class ChooseWidgetActivity : BaseDiLceMvpActivity<ChooseWidgetComponent,
        RecyclerView,
        WidgetModel,
        ChooseWidgetView,
        ChooseWidgetPresenter>(),
        ChooseWidgetView {

    companion object {
        val RESULT_KEY_CHOSE_WIDGET = 478
        val RESULT_EXTRA_WIDGET = "RESULT_EXTRA_WIDGET"

        fun startForResult(activity: Activity) {
            RxPermissions.getInstance(activity)
                    .request(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe { granted ->
                        if (granted) {
                            val intent = Intent(activity, ChooseWidgetActivity::class.java)
                            activity.startActivityForResult(intent, ChooseWidgetActivity.RESULT_KEY_CHOSE_WIDGET)
                        } else {
                            AlertDialog.Builder(activity)
                                    .setTitle(R.string.warning)
                                    .setMessage(R.string.error_location_permission)
                                    .setPositiveButton(R.string.ok, null)
                                    .show()
                        }
                    }
        }
    }

    private lateinit var adapter: WidgetsAdapter

    /* inject */

    override fun createComponent(): ChooseWidgetComponent {
        return SmartMirrorApp
                .sessionActivityComponentBuilders!![ChooseWidgetActivity::class.java]!!
                .build() as ChooseWidgetComponent
    }

    override fun injectMembers(instance: ChooseWidgetComponent) {
        instance.injectMembers(this)
    }

    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_widget)
        setupToolbar()
        adapter = WidgetsAdapter(
                context,
                { presenter.onChooseWidget(it) },
                { widgetData: WidgetData, widget: Widget<*> -> presenter.updateWidget(widgetData, widget) })
        contentView.adapter = adapter
        loadData(false)
    }

    override fun onResume() {
        super.onResume()
        presenter.clearWidgetsUpdaters()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWidgetUpdate(widgetData: WidgetData) {
        adapter.onUpdateItem(widgetData)
    }

    override fun onReturnWidgetConfiguration(widgetConfigurationModel: WidgetConfigurationModel) {
        val intent = Intent()
        intent.putExtra(ChooseWidgetActivity.RESULT_EXTRA_WIDGET, widgetConfigurationModel)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    /* lce */

    override fun setData(data: WidgetModel) {
        adapter.addModel(data)
    }

    override fun loadData(pullToRefresh: Boolean) {
        presenter.fetchWidgets(pullToRefresh)
    }

    /* private methods */

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
    }
}
