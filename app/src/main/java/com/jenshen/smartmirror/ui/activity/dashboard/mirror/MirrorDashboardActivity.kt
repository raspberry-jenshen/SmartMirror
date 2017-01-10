package com.jenshen.smartmirror.ui.activity.dashboard.mirror

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Surface.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.github.jinatonic.confetti.ConfettiManager
import com.jenshen.awesomeanimation.AwesomeAnimation
import com.jenshen.awesomeanimation.AwesomeAnimation.SizeMode.SCALE
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.firebase.model.configuration.MirrorConfiguration
import com.jenshen.smartmirror.data.firebase.model.configuration.OrientationMode
import com.jenshen.smartmirror.data.firebase.model.configuration.WidgetConfiguration
import com.jenshen.smartmirror.data.firebase.model.tuner.TunerInfo
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetSize
import com.jenshen.smartmirror.data.model.widget.PrecipitationModel
import com.jenshen.smartmirror.data.model.widget.WidgetKey
import com.jenshen.smartmirror.di.component.activity.dashboard.mirror.MirrorDashboardComponent
import com.jenshen.smartmirror.ui.activity.base.BaseMirrorDiMvpActivity
import com.jenshen.smartmirror.ui.activity.signup.mirror.SignUpMirrorActivity
import com.jenshen.smartmirror.ui.mvp.presenter.dashboard.mirror.MirrorDashboardPresenter
import com.jenshen.smartmirror.ui.mvp.view.dashboard.mirror.MirrorDashboardView
import com.jenshen.smartmirror.ui.view.widget.Widget
import com.jenshen.smartmirror.util.Optional
import com.jenshen.smartmirror.util.asCircleBitmap
import com.jenshen.smartmirror.util.getBitmap
import com.jenshen.smartmirror.util.widget.createWidget
import com.tbruyelle.rxpermissions2.RxPermissions
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_dashboard_mirror.*


class MirrorDashboardActivity : BaseMirrorDiMvpActivity<MirrorDashboardComponent, MirrorDashboardView, MirrorDashboardPresenter>(), MirrorDashboardView {

    private var confettiManager: ConfettiManager? = null

    @PrecipitationModel.PrecipitationType
    private var type: Int = PrecipitationModel.UNKNOWN

    companion object {

        fun start(context: Context) {
            startRoot(context, false)
        }

        fun startRoot(context: Context, isNeedToClear: Boolean) {
            RxPermissions.getInstance(context)
                    .request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CALENDAR)
                    .subscribe { granted ->
                        if (granted) {
                            val intent = Intent(context, MirrorDashboardActivity::class.java)
                            if (isNeedToClear) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
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

    /* inject */

    override fun createComponent(): MirrorDashboardComponent {
        return SmartMirrorApp
                .sessionActivityComponentBuilders!![MirrorDashboardActivity::class.java]!!
                .build() as MirrorDashboardComponent
    }

    override fun injectMembers(instance: MirrorDashboardComponent) {
        instance.injectMembers(this)
    }

    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_mirror)
        widgetContainer.isEnabled = false
    }

    /* callbacks */

    override fun showSignUpScreen() {
        val intent = Intent(context, SignUpMirrorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun onWidgetUpdate(infoData: WidgetData) {
        widgetContainer.widgets
                .find {
                    val widgetKey = it.tag as WidgetKey
                    (widgetKey.key == infoData.widgetKey.key) && widgetKey.number == infoData.widgetKey.number
                }
                ?.apply { presenter.updateWidget(infoData, getChildAt(0) as Widget<*>) }
    }

    override fun changeMirrorConfiguration(mirrorConfiguration: MirrorConfiguration) {
        val build = AwesomeAnimation.Builder(widgetContainer)
                .setSizeX(SCALE, 1f, 0f)
                .setSizeY(SCALE, 1f, 0f)
                .setAlpha(1f, 0f)
                .build()
        build.animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                while (widgetContainer.widgets.size != 0) {
                    widgetContainer.removeWidgetView(widgetContainer.widgets.iterator().next())
                }
                widgetContainer.setRowCount(mirrorConfiguration.containerSize.row)
                widgetContainer.setColumnCount(mirrorConfiguration.containerSize.column)
                mirrorConfiguration.widgets?.forEach { configureWidget(it.value) }
                widgetContainer.requestLayout()

                AwesomeAnimation.Builder(widgetContainer)
                        .setSizeX(SCALE, 0f, 1f)
                        .setSizeY(SCALE, 0f, 1f)
                        .setAlpha(0f, 1f)
                        .build()
                        .start()
            }
        })
        build.start()
    }

    override fun onPrecipitationUpdate(model: PrecipitationModel) {
        if (type != model.type) {
            if (confettiManager != null) {
                confettiManager!!.terminate()
            }
            confettiManager = model.getConfettiManager(context, contentView)
            confettiManager!!.animate()
        }
    }

    override fun enableUserInfo(userInfoData: Optional<TunerInfo>) {
        if (userInfoData.isPresent) {
            val avatarUrl = userInfoData.get().avatarUrl
            avatar.visibility = VISIBLE
            nikeName.visibility = VISIBLE

            nikeName.text = userInfoData.get().nikeName
            if (avatarUrl == null) {
                avatar.setImageBitmap(getBitmap(context, R.drawable.ic_demo_avatar).asCircleBitmap())
            } else {
                Glide.with(context)
                        .load(Uri.parse(avatarUrl))
                        .bitmapTransform(CropCircleTransformation(context))
                        .into(avatar)
            }
        } else {
            nikeName.visibility = GONE
            avatar.visibility = GONE
        }
    }

    override fun enablePrecipitation(enable: Boolean) {
        if (!enable && confettiManager != null) {
            confettiManager!!.terminate()
        }
        presenter.enablePrecipitation(enable)
    }

    override fun onChangeOrientation(orientationMode: OrientationMode) {
        val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val orientation = display.rotation
        val mode = when (orientation) {
            ROTATION_0 -> OrientationMode.PORTRAIT
            ROTATION_90 -> OrientationMode.LANDSCAPE
            ROTATION_180 -> OrientationMode.REVERSE_PORTRAIT
            ROTATION_270 -> OrientationMode.REVERSE_LANDSCAPE
            else -> OrientationMode.PORTRAIT
        }

        if (orientationMode != mode) {
            when (orientationMode) {
                OrientationMode.PORTRAIT -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                OrientationMode.LANDSCAPE -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                OrientationMode.REVERSE_PORTRAIT -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                OrientationMode.REVERSE_LANDSCAPE -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
            }
        }
    }

    /* private methods */

    private fun configureWidget(configuration: WidgetConfiguration) {
        val widget = createWidget(configuration.widgetKey, WidgetSize(), context)
        val sameWidgetsCount = widgetContainer.widgets.filter { (it.tag as WidgetKey).key == configuration.widgetKey }.size
        val widgetKey = WidgetKey(configuration.widgetKey, sameWidgetsCount)
        widget.tag = widgetKey
        presenter.addWidgetUpdater(widgetKey)

        val position = widget.widgetPosition

        position.topLeftColumnLine = configuration.topLeftCorner.column
        position.topLeftRowLine = configuration.topLeftCorner.row

        position.topRightColumnLine = configuration.topRightCorner.column
        position.topRightRowLine = configuration.topRightCorner.row

        position.bottomLeftColumnLine = configuration.bottomLeftCorner.column
        position.bottomLeftRowLine = configuration.bottomLeftCorner.row

        position.bottomRightColumnLine = configuration.bottomRightCorner.column
        position.bottomRightRowLine = configuration.bottomRightCorner.row

        widgetContainer.addWidgetView(widget)
    }
}