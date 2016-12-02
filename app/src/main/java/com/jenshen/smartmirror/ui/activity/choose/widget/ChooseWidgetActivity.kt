package com.jenshen.smartmirror.ui.activity.choose.widget

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.jenshen.compat.base.view.impl.mvp.lce.component.lce.BaseDiLceMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.model.WidgetModel
import com.jenshen.smartmirror.di.component.activity.choose.widget.ChooseWidgetComponent
import com.jenshen.smartmirror.ui.adapter.widgets.WidgetsAdapter
import com.jenshen.smartmirror.ui.mvp.presenter.choose.widget.ChooseWidgetPresenter
import com.jenshen.smartmirror.ui.mvp.view.choose.widget.ChooseWidgetView
import kotlinx.android.synthetic.main.activity_qr_scanner.*


class ChooseWidgetActivity : BaseDiLceMvpActivity<ChooseWidgetComponent,
        RecyclerView,
        WidgetModel,
        ChooseWidgetView,
        ChooseWidgetPresenter>(),
        ChooseWidgetView {

    companion object {
        val RESULT_KEY_CHOSE_WIDGET = 478
        val RESULT_EXTRA_WIDGET = "RESULT_EXTRA_WIDGET"
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
        setContentView(R.layout.activity_choose_mirror)
        setupToolbar()
        adapter = WidgetsAdapter(context, {
            val intent = Intent()
            intent.putExtra(ChooseWidgetActivity.RESULT_EXTRA_WIDGET, it)
            setResult(Activity.RESULT_OK, intent)
            finish()
        })
        contentView.adapter = adapter
        loadData(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this);
            }
        }
        return super.onOptionsItemSelected(item)
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
