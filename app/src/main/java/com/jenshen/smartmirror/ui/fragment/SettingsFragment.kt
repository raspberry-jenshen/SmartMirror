package com.jenshen.smartmirror.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.interactor.firebase.auth.AuthInteractor
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.manager.session.SessionManager
import com.jenshen.smartmirror.ui.activity.start.StartActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    protected lateinit var sessionManager: SessionManager
    @Inject
    protected lateinit var preferencesManager: PreferencesManager

    private lateinit var compositeDisposable: CompositeDisposable

    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
        SmartMirrorApp.userComponent!!.inject(this)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference_settings)

        val buttonLogout = findPreference(getString(R.string.preference_logout_key))
        buttonLogout.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            preferencesManager.logout()
                    .doOnComplete { sessionManager.logout() }
                    .doOnComplete { SmartMirrorApp.releaseUserComponent() }
                    .doOnSubscribe { compositeDisposable.add(it) }
                    .subscribe({
                        val intent = Intent(context, StartActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }, {})
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}