package com.jenshen.smartmirror.ui.view.widget


import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.widget.info.currency.CurrencyWidgetData

class ExchangeRatesView : CoordinatorLayout, Widget<CurrencyWidgetData> {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun updateWidget(widgetData: CurrencyWidgetData) {

    }

    /* private methods */

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_forecast, this)
    }
}
