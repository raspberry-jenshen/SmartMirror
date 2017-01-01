package com.jenshen.smartmirror.data.entity.currency

import java.util.*

class ExchangeRatesResponse(val disclaimer: String,
                            val license: String,
                            val timestamp: Date,
                            val base: String,
                            val rates: Rates)