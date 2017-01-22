package com.jenshen.smartmirror.ui.view.widget


import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.widget.info.currency.ExchangeRatesWidgetData
import com.jenshen.smartmirror.util.toDayMonth
import kotlinx.android.synthetic.main.view_exchange_rates.view.*

class ExchangeRatesView : ConstraintLayout, Widget<ExchangeRatesWidgetData> {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun updateWidget(widgetData: ExchangeRatesWidgetData) {
        date.text = widgetData.exchangeRatesResponse.timestamp.toDayMonth()
        mainCurrency.text = resources.getString(R.string.widget_exchange_rates_currency_UAH)
        val rates = widgetData.exchangeRatesResponse.rates
        currencyIcon0.text = resources.getString(R.string.widget_exchange_rates_currency_UAH)
        currencyIcon1.text = resources.getString(R.string.widget_exchange_rates_currency_EUR)
        currencyIcon2.text = resources.getString(R.string.widget_exchange_rates_currency_RUB)
        currencyIcon3.text = resources.getString(R.string.widget_exchange_rates_currency_GBP)
        currency0.text = String.format("%(.2f", rates.UAH)
        currency1.text = String.format("%(.2f", rates.UAH / rates.EUR)
        currency2.text = String.format("%(.2f", rates.UAH / rates.RUB)
        currency3.text = String.format("%(.2f", rates.UAH / rates.GBP)
    }

    /* private methods */

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_exchange_rates, this)
    }
}
