package com.jenshen.smartmirror.util


import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.AppCompatDrawableManager


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

fun Bitmap.scale(wantedWidth: Float, wantedHeight: Float): Bitmap {
    val output = Bitmap.createBitmap(wantedWidth.toInt(), wantedHeight.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val m = Matrix()
    m.setScale(wantedWidth / this.width, wantedHeight / this.height)
    canvas.drawBitmap(this, m, Paint())
    return output
}

fun Bitmap.scaleCenterCrop(newWidth: Float, newHeight: Float): Bitmap {
    val sourceWidth = this.width
    val sourceHeight = this.height

    // Compute the scaling factors to fit the new height and width, respectively.
    // To cover the final image, the final scaling will be the bigger
    // of these two.
    val xScale = newWidth / sourceWidth
    val yScale = newHeight / sourceHeight
    val scale = Math.max(xScale, yScale)

    // Now get the size of the source bitmap when scaled
    val scaledWidth = scale * sourceWidth
    val scaledHeight = scale * sourceHeight

    // Let's find out the upper left coordinates if the scaled bitmap
    // should be centered in the new size give by the parameters
    val left = (newWidth - scaledWidth) / 2
    val top = (newHeight - scaledHeight) / 2

    // The target rectangle for the new, scaled version of the source bitmap will now
    // be
    val targetRect = RectF(left, top, left + scaledWidth, top + scaledHeight)

    // Finally, we create a new bitmap of the specified size and draw our new,
    // scaled bitmap onto it.
    val dest = Bitmap.createBitmap(newWidth.toInt(), newHeight.toInt(), this.config)
    val canvas = Canvas(dest)
    canvas.drawBitmap(this, null, targetRect, null)
    return dest
}


fun Bitmap.cutBottom(width: Int, height: Int): Bitmap {
    val cutBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(cutBitmap)
    val desRect = Rect(0, 0, width, height)
    val srcRect = Rect(0, 0, this.width, this.height - (this.height - height))
    canvas.drawBitmap(this, srcRect, desRect, null)
    return cutBitmap
}

fun getBitmap(context: Context, drawableId: Int): Bitmap {
    var drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        drawable = DrawableCompat.wrap(drawable).mutate()
    }
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    } else {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
private fun getBitmap(vectorDrawable: VectorDrawable): Bitmap {
    val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)
    return bitmap
}