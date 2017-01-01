package com.jenshen.smartmirror.manager.preference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.currency.ExchangeRatesResponse
import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.data.entity.session.Session
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.entity.weather.day.WeatherForCurrentDayResponse
import com.jenshen.smartmirror.data.entity.weather.forecast.WeatherForecastResponse
import com.jenshen.smartmirror.util.Optional
import io.reactivex.Completable

class SharedPreferencesManager : PreferencesManager {

    private val mSharedPreferences: SharedPreferences
    private val mGson: Gson
    private val mContext: Context

    constructor(context: Context, sharePreferences: SharedPreferences, gson: Gson) {
        mContext = context
        mSharedPreferences = sharePreferences
        mGson = gson
    }

    override fun sighIn(session: Session, isMirror: Boolean) {
        saveSession(session)
        setIsMirror(isMirror)
    }

    override fun logout(): Completable = Completable.fromCallable {
        val editor = mSharedPreferences.edit()
        //remove it if needed editor.remove(mContext.getString(R.string.preference_key_user))
        editor.remove(mContext.getString(R.string.preference_key_is_mirror))
        editor.apply()
    }

    override fun getSession(): Session? {
        val json = mSharedPreferences.getString(mContext.getString(R.string.preference_key_user), null) ?: return null
        if (isMirror()) {
            return mGson.fromJson(json, MirrorSession::class.java)
        } else {
            return mGson.fromJson(json, TunerSession::class.java)
        }

    }

    override fun isMirror(): Boolean {
        return mSharedPreferences.getBoolean(mContext.getString(R.string.preference_key_is_mirror), false)
    }

    override fun saveCurrentWeather(weatherForCurrentDayResponse: WeatherForCurrentDayResponse) {
        saveModel(mContext.getString(R.string.preference_key_current_weather), weatherForCurrentDayResponse)
    }

    override fun saveWeatherForecast(weatherForecastResponse: WeatherForecastResponse) {
        saveModel(mContext.getString(R.string.preference_key_weather_forecast), weatherForecastResponse)
    }

    override fun saveExchangeRates(exchangeRatesResponse: ExchangeRatesResponse) {
        saveModel(mContext.getString(R.string.preference_key_exchange_rates), exchangeRatesResponse)
    }

    override fun getCurrentWeather(): WeatherForCurrentDayResponse? {
        return getModel(mContext.getString(R.string.preference_key_current_weather), WeatherForCurrentDayResponse::class.java)
    }

    override fun getWeatherForecast(): WeatherForecastResponse? {
        return getModel(mContext.getString(R.string.preference_key_weather_forecast), WeatherForecastResponse::class.java)
    }

    override fun getExchangeRates(): ExchangeRatesResponse? {
        return getModel(mContext.getString(R.string.preference_key_exchange_rates), ExchangeRatesResponse::class.java)
    }


    /* private methods */

    private fun saveSession(session: Session) {
        saveModel(mContext.getString(R.string.preference_key_user), session)
    }

    private fun setIsMirror(isMirror: Boolean) {
        mSharedPreferences.edit().putBoolean(mContext.getString(R.string.preference_key_is_mirror), isMirror).apply()
    }

    private fun saveModel(key: String, any: Any) {
        val stringValue = mGson.toJson(any)
        mSharedPreferences.edit().putString(key, stringValue).apply()
    }

    private fun <T> getModel(key: String, clazz: Class<T>): T? {
        val json = mSharedPreferences.getString(key, null) ?: return null
        return mGson.fromJson(json, clazz)
    }
}
