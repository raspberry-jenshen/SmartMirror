package com.jenshen.smartmirror.data.model.widget

import android.content.Context
import android.support.annotation.IntDef
import android.view.ViewGroup
import com.github.jinatonic.confetti.ConfettiManager
import com.github.jinatonic.confetti.ConfettiSource
import com.github.jinatonic.confetti.confetto.BitmapConfetto
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.util.getBitmap
import com.jenshen.smartmirror.util.scale

class PrecipitationModel private constructor(@PrecipitationType val type: Int) {

    companion object {

        fun createFromWeatherData(type: Int): PrecipitationModel {
            if (type >= 500 && type <= 531) {
                return PrecipitationModel(RAIN)
            } else if (type >= 500 && type <= 531) {
                return PrecipitationModel(SNOW)
            } else {
                return PrecipitationModel(UNKNOWN)
            }
        }

        fun createFromPrecipitationType(@PrecipitationType type: Int): PrecipitationModel {
            return PrecipitationModel(type)
        }

        const val UNKNOWN = 0
        const val RAIN = 1
        const val SNOW = 2
    }

    fun getResId(): Int {
        return when (type) {
            SNOW -> R.drawable.ic_snowflake
            RAIN -> R.drawable.ic_drop
            else -> throw RuntimeException("Can't support this type")
        }
    }

    fun getConfettiManager(context: Context, container: ViewGroup): ConfettiManager {
        val velocitySlow1 = context.resources.getDimensionPixelOffset(R.dimen.default_velocity_slow1)
        val velocityNormal = context.resources.getDimensionPixelOffset(R.dimen.default_velocity_normal)
        val velocityFast3 = context.resources.getDimensionPixelOffset(R.dimen.default_velocity_fast3)

        val confettiManager = when (type) {
            SNOW -> {
                val size = context.resources.getDimensionPixelSize(R.dimen.widget_confetti_snow_size)
                val bitmap = getBitmap(context, getResId()).scale(size.toFloat(), size.toFloat())
                val source = ConfettiSource(0, -size, container.width, -size)
                ConfettiManager(context, { BitmapConfetto(bitmap) }, source, container)
                        .setVelocityX(0f, velocitySlow1.toFloat())
                        .setVelocityY(velocityNormal.toFloat(), velocitySlow1.toFloat())
                        .setRotationalVelocity(180f, 90f)
                        .setEmissionRate(100.toFloat())
            }
            RAIN -> {
                val size = context.resources.getDimensionPixelSize(R.dimen.widget_confetti_rain_size)
                val bitmap = getBitmap(context, getResId()).scale(size.toFloat(), size.toFloat())
                val source = ConfettiSource(0, -size, container.width, -size)
                ConfettiManager(context, { BitmapConfetto(bitmap) }, source, container)
                        .setVelocityX(0f, velocitySlow1.toFloat())
                        .setVelocityY(velocityFast3.toFloat(), velocitySlow1.toFloat())
                        .setEmissionRate(300.toFloat())
            }
            else -> throw RuntimeException("Can't support this type")
        }

        return confettiManager
                .setEmissionDuration(ConfettiManager.INFINITE_DURATION)
    }

    @IntDef(UNKNOWN.toLong(), RAIN.toLong(), SNOW.toLong())
    @Retention(AnnotationRetention.SOURCE)
    annotation class PrecipitationType
}