package com.jenshen.smartmirror.ui.view.widget


import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.widget.info.CalendarEventsWidgetData
import com.jenshen.smartmirror.data.entity.widget.info.currency.ExchangeRatesWidgetData
import com.jenshen.smartmirror.util.toDayMonth
import kotlinx.android.synthetic.main.view_exchange_rates.view.*

class CalendarEventsView : LinearLayout, Widget<CalendarEventsWidgetData> {


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun updateWidget(widgetData: CalendarEventsWidgetData) {
        val events = widgetData.events

        LayoutInflater.from(context).inflate(R.layout.view_calendar_event, this)

    }

    /* private methods */

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_exchange_rates, this)
    }
}
