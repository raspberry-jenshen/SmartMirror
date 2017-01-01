package com.jenshen.smartmirror.data.api.currency

import com.jenshen.smartmirror.data.entity.currency.ExchangeRatesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyApi {

    companion object {
        const val API_URL = "https://openexchangerates.org/api/"
        const val UAH_CURRENCY = "UAH"
    }

    @GET("latest.json")
    fun getExchangeRates(@Query("app_id") id: String,
                         @Query("base") base: String,
                         @Query("symbols") symbols: String): Single<ExchangeRatesResponse>
}