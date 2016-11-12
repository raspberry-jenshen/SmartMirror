package com.jenshen.smartmirror.util


import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.support.v4.content.ContextCompat

fun Bitmap.asCircleBitmap(): Bitmap {
    val output = Bitmap.createBitmap(this.width,
            this.height, Bitmap.Config.ARGB_8888)
    val length = if (this.width > this.height) this.height else this.width
    val canvas = Canvas(output)
    val color = Color.RED
    val paint = Paint()
    val rect = Rect(0, 0, length, length)
    val rectF = RectF(rect)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    canvas.drawOval(rectF, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)
    this.recycle()
    return output
}

fun getBitmap(context: Context, drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    } else if (drawable is VectorDrawable) {
        return getBitmap(drawable)
    } else {
        throw IllegalArgumentException("unsupported drawable type")
    }
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
private fun getBitmap(vectorDrawable: VectorDrawable): Bitmap {
    val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)
    return bitmap
}