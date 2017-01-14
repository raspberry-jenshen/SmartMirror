package com.jenshen.smartmirror.ui.fragment.settings.app

import android.content.Intent
import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.crashlytics.android.Crashlytics
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.manager.session.SessionManager
import com.jenshen.smartmirror.ui.activity.add.widget.AddWidgetActivity
import com.jenshen.smartmirror.ui.activity.choose.account.ChooseAccountActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    protected lateinit var sessionManager: SessionManager
    @Inject
    protected lateinit var preferencesManager: PreferencesManager
    @Inject
    protected lateinit var dispatcher: FirebaseJobDispatcher

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
                    .doOnComplete { dispatcher.cancelAll() }
                    .doOnComplete { SmartMirrorApp.releaseUserComponent() }
                    .doOnSubscribe { compositeDisposable.add(it) }
                    .subscribe({
                        val intent = Intent(context, ChooseAccountActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }, { Crashlytics.logException(it) })
            true
        }

        val buttonAddWidget = findPreference(getString(R.string.preference_add_widgets))
        buttonAddWidget.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(context, AddWidgetActivity::class.java)
            startActivity(intent)
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}