package com.jenshen.smartmirror.ui.view

import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.StyleRes
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.jenshen.smartmirror.R
import com.jenshensoft.widgetview.WidgetContainerLayout

class WidgetScaleLayout : WidgetContainerLayout {

    private var textSize_multiplier = 1.0f

    constructor(context: Context) : super(context) {
        init()
        if (!isInEditMode) {
            initAttr(null)
        }
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        if (!isInEditMode) {
            initAttr(attrs)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
        if (!isInEditMode) {
            initAttr(attrs)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int, @StyleRes defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
        if (!isInEditMode) {
            initAttr(attrs)
        }
    }

    override fun onViewAdded(child: View) {
        findViews(child, TextView::class.java, { it.textSize *= textSize_multiplier})
        super.onViewAdded(child)
    }

    private fun initAttr(attrs: AttributeSet?) {
        if (attrs != null) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.WidgetScaleLayout)
            try {
                textSize_multiplier = attributes.getFloat(R.styleable.WidgetScaleLayout_scale_textSize_multiplier, textSize_multiplier)
            } finally {
                attributes.recycle()
            }
        }
    }

    private fun init() {

    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> findViews(child: View, clazz: Class<T>, function: (T) -> Unit) {
        if (clazz.isInstance(child)) {
            function(child as T)
        } else if (child is ViewGroup) {
            for (i in 0..child.childCount - 1) {
                findViews(child.getChildAt(i), clazz, function)
            }
        }
    }
}