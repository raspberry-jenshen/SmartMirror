package com.jenshen.smartmirror.data.entity.widget.info.currency

import com.jenshen.smartmirror.data.entity.currency.ExchangeRatesResponse
import com.jenshen.smartmirror.data.entity.widget.info.WidgetData
import com.jenshen.smartmirror.data.model.widget.WidgetKey

class ExchangeRatesWidgetData(widgetKey: WidgetKey, val exchangeRatesResponse: ExchangeRatesResponse) : WidgetData(widgetKey)
