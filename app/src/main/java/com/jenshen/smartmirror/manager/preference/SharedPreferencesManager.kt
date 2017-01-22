package com.jenshen.smartmirror.manager.preference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.Job
import com.jenshen.smartmirror.data.entity.currency.ExchangeRatesResponse
import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.data.entity.session.Session
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.entity.weather.day.WeatherForCurrentDayResponse
import com.jenshen.smartmirror.data.entity.weather.forecast.WeatherForecastResponse
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

    override fun logout(isNeedToRemoveUser: Boolean): Completable = Completable.fromCallable {
        val editor = mSharedPreferences.edit()
        if(isNeedToRemoveUser) {
            editor.remove(mContext.getString(R.string.preference_key_user))
        } else {
            val session = getSession()
            if (session != null) {
                session.isLogOut = true
                saveSession(session)
            }
        }
        editor.remove(mContext.getString(R.string.preference_key_is_mirror))
        editor.remove(mContext.getString(R.string.preference_key_current_weather))
        editor.remove(mContext.getString(R.string.preference_key_weather_forecast))
        editor.remove(mContext.getString(R.string.preference_key_exchange_rates))
        editor.remove(mContext.getString(R.string.preference_key_list_of_jobs))
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

    override fun addJob(job: Job) {
        var list = getJobs()
        if (list == null) {
            list = mutableListOf<Job>()
        }
        list.add(job)
        val key = mContext.getString(R.string.preference_key_list_of_jobs)
        saveModel(key, list)
    }

    override fun getJobs(): MutableList<Job>? {
        val key = mContext.getString(R.string.preference_key_list_of_jobs)
        val typeToken = object : TypeToken<MutableList<Job>>() {}
        return getModel(key, typeToken)
    }

    override fun deleteJob(job: Job) {
        val list = getJobs()?: return
        if (job.currentWidgetKey == null) {
            if (job.widgetKey == null) {
                if (job.configurationKey == null) {
                    val itemsToDelete = list.filter { it.mirrorKey == job.mirrorKey }.toCollection(mutableListOf())
                    list.removeAll(itemsToDelete)
                } else {
                    val itemsToDelete = list.filter { it.configurationKey == job.configurationKey }.toCollection(mutableListOf())
                    list.removeAll(itemsToDelete)
                }
            } else {
                val itemsToDelete = list.filter { it.widgetKey == job.widgetKey }.toCollection(mutableListOf())
                list.removeAll(itemsToDelete)
            }
        } else {
            list.remove(job)
        }
        val key = mContext.getString(R.string.preference_key_list_of_jobs)
        saveModel(key, list)
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

    private fun <T> getModel(key: String, typeToken: TypeToken<T>): T? {
        val json = mSharedPreferences.getString(key, null) ?: return null
        return mGson.fromJson(json, typeToken.type)
    }
}
