package com.jenshen.smartmirror.data.firebase.model.configuration

import android.content.Context
import com.jenshen.smartmirror.R


enum class OrientationMode(val index: Int) {
    PORTRAIT(0),
    LANDSCAPE(1),
    REVERSE_PORTRAIT(2),
    REVERSE_LANDSCAPE(3);

    fun getName(context: Context): String {
        return when (this) {
            OrientationMode.PORTRAIT -> context.getString(R.string.mirror_settings_portrait)
            OrientationMode.LANDSCAPE -> context.getString(R.string.mirror_settings_landscape)
            OrientationMode.REVERSE_PORTRAIT -> context.getString(R.string.mirror_settings_reverse_portrait)
            OrientationMode.REVERSE_LANDSCAPE -> context.getString(R.string.mirror_settings_reverse_landscape)
        }
    }

    companion object {
        fun toOrientationMode(index: Int): OrientationMode {
            return when (index) {
                OrientationMode.PORTRAIT.index -> OrientationMode.PORTRAIT
                OrientationMode.LANDSCAPE.index -> OrientationMode.LANDSCAPE
                OrientationMode.REVERSE_PORTRAIT.index -> OrientationMode.REVERSE_PORTRAIT
                OrientationMode.REVERSE_LANDSCAPE.index -> OrientationMode.REVERSE_LANDSCAPE
                else -> OrientationMode.PORTRAIT
            }
        }
    }
}
