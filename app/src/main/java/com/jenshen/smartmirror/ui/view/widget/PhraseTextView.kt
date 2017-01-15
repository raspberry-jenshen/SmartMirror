package com.jenshen.smartmirror.ui.view.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.ViewGroup
import com.jenshen.smartmirror.R

import com.jenshen.smartmirror.data.entity.widget.info.PhraseWidgetData
import com.jenshen.smartmirror.ui.view.ScaledTextView

class PhraseTextView : ScaledTextView, Widget<PhraseWidgetData> {

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        gravity = CENTER
        setTextColor(ContextCompat.getColor(context, R.color.white))
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun updateWidget(phraseWidgetData: PhraseWidgetData) {
        text = phraseWidgetData.phrase
    }
}
