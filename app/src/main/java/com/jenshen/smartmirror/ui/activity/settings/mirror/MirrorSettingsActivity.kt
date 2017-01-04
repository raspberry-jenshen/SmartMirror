package com.jenshen.smartmirror.ui.activity.settings.mirror

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.firebase.model.configuration.OrientationMode
import com.jenshen.smartmirror.data.model.configuration.ConfigurationSettingsModel
import com.jenshen.smartmirror.di.component.activity.settings.mirror.MirrorSettingsComponent
import com.jenshen.smartmirror.ui.mvp.presenter.settings.mirror.MirrorSettingsPresenter
import com.jenshen.smartmirror.ui.mvp.view.settings.mirror.MirrorSettingsView
import kotlinx.android.synthetic.main.activity_mirror_settings.*


class MirrorSettingsActivity : BaseDiMvpActivity<MirrorSettingsComponent, MirrorSettingsView, MirrorSettingsPresenter>(), MirrorSettingsView {

    companion object {
        private val EXTRA_MIRROR_CONFIGURATION_KEY = "EXTRA_MIRROR_CONFIGURATION_KEY"

        fun start(context: Context, configurationKey: String) {
            val intent = Intent(context, MirrorSettingsActivity::class.java)
            intent.putExtra(EXTRA_MIRROR_CONFIGURATION_KEY, configurationKey)
            context.startActivity(intent)
        }
    }

    private lateinit var model: ConfigurationSettingsModel

    /* inject */

    override fun createComponent(): MirrorSettingsComponent {
        return SmartMirrorApp
                .sessionActivityComponentBuilders!![MirrorSettingsActivity::class.java]!!
                .build() as MirrorSettingsComponent
    }

    override fun injectMembers(instance: MirrorSettingsComponent) {
        instance.injectMembers(this)
    }

    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mirror_settings)
        setupToolbar()
        presenter.updateModel(intent.getStringExtra(EXTRA_MIRROR_CONFIGURATION_KEY))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /* callbacks */

    override fun onModelUpdated(model: ConfigurationSettingsModel) {
        this.model = model
        enableWeatherAnimation.isChecked = model.isPrecipitationEnabled
        showAvatar.isChecked = model.isUserInfoEnabled
        mode.text = model.orientationMode.getName(context)
        enableWeatherAnimation.setOnCheckedChangeListener({ buttonView, isChecked -> presenter.enablePrecipitationOnMirror(model.configurationKay, isChecked) })
        showAvatar.setOnCheckedChangeListener({ buttonView, isChecked -> presenter.enableUserInfoOnMirror(model.configurationKay, isChecked) })
        selectOrientationMode.setOnClickListener { showOrientationDialog(model.orientationMode.index) }
    }

    override fun onOrientationChanged(orientationMode: OrientationMode) {
        model.orientationMode = orientationMode
        mode.text = model.orientationMode.getName(context)
    }

    /* private methods */

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
    }

    private fun showOrientationDialog(selectedItem: Int = -1) {
        val items = arrayOf<CharSequence>(
                getString(R.string.mirror_settings_portrait),
                getString(R.string.mirror_settings_landscape),
                getString(R.string.mirror_settings_reverse_portrait),
                getString(R.string.mirror_settings_reverse_landscape))
        var position = selectedItem
        AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(items, selectedItem, { dialog, item ->
                    position = item
                })
                .setPositiveButton(R.string.ok, { dialogInterface, i ->
                    if (position != selectedItem) {
                        presenter.setOrientationMode(model.configurationKay, OrientationMode.toOrientationMode(position))
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show()
    }
}
