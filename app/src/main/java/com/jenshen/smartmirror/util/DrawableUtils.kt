package com.jenshen.smartmirror.util

import android.annotation.TargetApi
import android.content.Context
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

fun View.tintWidget(color: Int) {
    val wrappedDrawable = DrawableCompat.wrap(this.background)
    DrawableCompat.setTint(wrappedDrawable, color)
    setBackground(wrappedDrawable, this)
}

fun getTintedDrawable(context: Context, @DrawableRes drawableResId: Int, color: Int): Drawable {
    val drawable = ContextCompat.getDrawable(context, drawableResId)
    drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    return drawable
}

fun setBackground(drawable: Drawable, view: View) {
    val sdk = Build.VERSION.SDK_INT
    if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
        view.setBackgroundDrawable(drawable)
    } else {
        setBackgroundV16(drawable, view)
    }
}

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
private fun setBackgroundV16(drawable: Drawable, view: View) {
    view.background = drawable
}

fun View.setColorFilter(view: View, colorFilter: ColorFilter?) {
    if (view is ImageView) {
        view.colorFilter = colorFilter
        if (colorFilter == null) {
            view.clearColorFilter()
        }
    } else if (view is ViewGroup) {
        val background = view.background
        if (background != null) {
            background.colorFilter = colorFilter
            if (colorFilter == null) {
                background.clearColorFilter()
            }
        }
        for (i in 0..view.childCount - 1) {
            setColorFilter(view.getChildAt(i), colorFilter)
        }
    }
}
