package com.jenshen.smartmirror.data.model.widget

import android.support.annotation.IntDef
import com.jenshen.smartmirror.R

class PrecipitationModel private constructor(@PrecipitationType val type: Int) {

    companion object {

        fun createFromWeatherData(type: Int): PrecipitationModel {
            if (type >= 500 && type <= 531) {
                return PrecipitationModel(RAIN)
            } else if (type >= 500 && type <= 531) {
                return PrecipitationModel(SNOW)
            } else {
                return PrecipitationModel(RAIN)
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
        return when(type) {
            SNOW -> R.drawable.ic_snowflake
            RAIN -> R.drawable.ic_drop
            else -> throw RuntimeException("Can't support this type")
        }
    }

    @IntDef(UNKNOWN.toLong(), RAIN.toLong(), SNOW.toLong())
    @Retention(AnnotationRetention.SOURCE)
    annotation class PrecipitationType
}