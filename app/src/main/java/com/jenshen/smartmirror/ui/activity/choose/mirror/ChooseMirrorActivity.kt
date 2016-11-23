package com.jenshen.smartmirror.ui.activity.choose.mirror

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.jenshen.compat.base.view.impl.mvp.lce.component.lce.BaseDiLceMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.model.MirrorModel
import com.jenshen.smartmirror.di.component.activity.choose.mirror.ChooseMirrorComponent
import com.jenshen.smartmirror.ui.activity.edit.mirror.EditMirrorActivity
import com.jenshen.smartmirror.ui.activity.qrScan.QRCodeScanActivity
import com.jenshen.smartmirror.ui.activity.settings.SettingsActivity
import com.jenshen.smartmirror.ui.adapter.SwipeToDeleteAdapter
import com.jenshen.smartmirror.ui.adapter.mirrors.MirrorsAdapter
import com.jenshen.smartmirror.ui.adapter.touch.SimpleItemTouchHelperCallback
import com.jenshen.smartmirror.ui.mvp.presenter.choose.mirror.ChooseMirrorPresenter
import com.jenshen.smartmirror.ui.mvp.view.choose.mirror.ChooseMirrorView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_choose_mirror.*


class ChooseMirrorActivity : BaseDiLceMvpActivity<ChooseMirrorComponent,
        RecyclerView,
        MirrorModel,
        ChooseMirrorView,
        ChooseMirrorPresenter>(),
        ChooseMirrorView, SwipeToDeleteAdapter.OnDeleteItemListener<MirrorModel> {


    private lateinit var adapter: MirrorsAdapter

    /* inject */

    override fun createComponent(): ChooseMirrorComponent {
        return SmartMirrorApp
                .sessionActivityComponentBuilders!![ChooseMirrorActivity::class.java]!!
                .build() as ChooseMirrorComponent
    }

    override fun injectMembers(instance: ChooseMirrorComponent) {
        instance.injectMembers(this)
    }

    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_mirror)
        setSupportActionBar(toolbar)
        adapter = MirrorsAdapter(context,
                { presenter.setMirrorIsWaitingForSubscriber(it.tunerSubscription.id) },
                { startActivity(Intent(this, EditMirrorActivity::class.java)) },
                { configurationId: String, mirrorModel: MirrorModel -> /*todo edit */ },
                { configurationId: String, mirrorModel: MirrorModel -> presenter.deleteConfigurationForMirror(configurationId, mirrorModel.key) },
                { configurationId: String, mirrorModel: MirrorModel -> presenter.setConfigurationIdForMirror(configurationId, mirrorModel.key) },
                this)
        contentView.adapter = adapter

        val callback = SimpleItemTouchHelperCallback(adapter, context, ItemTouchHelper.LEFT)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(contentView)

        loadData(false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == QRCodeScanActivity.RESULT_KEY_QR_CODE && resultCode != Activity.RESULT_OK && data == null) {
            return
        }
        presenter.subscribeOnMirror(data!!.getStringExtra(QRCodeScanActivity.RESULT_EXTRA_MIRROR_ID))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_tuner_dashboard, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_item_menu -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.scan_QrCode_item_menu -> {
                RxPermissions.getInstance(this)
                        .request(Manifest.permission.CAMERA)
                        .subscribe { granted ->
                            if (granted) {
                                val intent = Intent(this, QRCodeScanActivity::class.java)
                                startActivityForResult(intent, QRCodeScanActivity.RESULT_KEY_QR_CODE)
                            } else {
                                AlertDialog.Builder(this)
                                        .setTitle(R.string.warning)
                                        .setMessage(R.string.error_camera_permission)
                                        .setPositiveButton(R.string.ok, null)
                                        .show()
                            }
                        }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /* lce */

    override fun loadData(pullToRefresh: Boolean) {
        presenter.fetchMirrors(pullToRefresh)
    }

    override fun setData(data: MirrorModel) {
        adapter.addModel(data)
    }

    /* callbacks */

    override fun onDeleteItem(position: Int, item: MirrorModel) {
        adapter.deleteModel(position)
        presenter.deleteSubscription(item.tunerSubscription)
    }
}
