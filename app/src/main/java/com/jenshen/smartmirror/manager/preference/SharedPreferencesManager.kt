package com.jenshen.smartmirror.manager.preference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.model.User
import io.reactivex.Completable
import io.reactivex.Single

class SharedPreferencesManager : PreferencesManager {

    private val mSharedPreferences: SharedPreferences
    private val mGson: Gson
    private val mContext: Context

    constructor(context: Context, sharePreferences: SharedPreferences, gson: Gson) {
        mContext = context
        mSharedPreferences = sharePreferences
        mGson = gson
    }

    override fun sighIn(user: User, isMirror : Boolean) {
        saveUser(user)
        setIsMirror(isMirror)
    }

    override fun logout(): Completable = Completable.fromCallable {
        val editor = mSharedPreferences.edit()
        //remove it if needed editor.remove(mContext.getString(R.string.preference_key_user))
        editor.remove(mContext.getString(R.string.preference_key_is_mirror))
        editor.apply()
    }

    override fun getUser(): User? {
        val json = mSharedPreferences.getString(mContext.getString(R.string.preference_key_user), null) ?: return null
        return mGson.fromJson(json, User::class.java)
    }

    override fun isMirror(): Boolean {
        return mSharedPreferences.getBoolean(mContext.getString(R.string.preference_key_is_mirror), false)
    }


    /* private methods */

    private fun saveUser(user: User) {
        val stringValue = mGson.toJson(user)
        mSharedPreferences.edit().putString(mContext.getString(R.string.preference_key_user), stringValue).apply()
    }

    private fun setIsMirror(isMirror: Boolean) {
        mSharedPreferences.edit().putBoolean(mContext.getString(R.string.preference_key_is_mirror), isMirror).apply()
    }
}
