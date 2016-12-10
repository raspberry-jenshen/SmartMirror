package com.jenshen.smartmirror.manager.preference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.session.MirrorSession
import com.jenshen.smartmirror.data.entity.session.Session
import com.jenshen.smartmirror.data.entity.session.TunerSession
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

    override fun sighIn(session: Session, isMirror : Boolean) {
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
        } else{
            return mGson.fromJson(json, TunerSession::class.java)
        }

    }

    override fun isMirror(): Boolean {
        return mSharedPreferences.getBoolean(mContext.getString(R.string.preference_key_is_mirror), false)
    }


    /* private methods */

    private fun saveSession(session: Session) {
        val stringValue = mGson.toJson(session)
        mSharedPreferences.edit().putString(mContext.getString(R.string.preference_key_user), stringValue).apply()
    }

    private fun setIsMirror(isMirror: Boolean) {
        mSharedPreferences.edit().putBoolean(mContext.getString(R.string.preference_key_is_mirror), isMirror).apply()
    }
}
