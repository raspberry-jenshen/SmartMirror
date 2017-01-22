package com.jenshen.smartmirror.ui.activity.signIn

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.di.component.activity.signIn.SignInComponent
import com.jenshen.smartmirror.ui.activity.choose.mirror.ChooseMirrorActivity
import com.jenshen.smartmirror.ui.activity.signup.tuner.SignUpTunerActivity
import com.jenshen.smartmirror.ui.mvp.presenter.signin.SignInPresenter
import com.jenshen.smartmirror.ui.mvp.view.signin.SignInView
import com.jenshen.smartmirror.util.reactive.onEditorActionObservable
import com.jenshen.smartmirror.util.reactive.onTextChangedObservable
import com.jenshen.smartmirror.util.validation.ValidationResult
import kotlinx.android.synthetic.main.activity_sign_in_tuner.*
import kotlinx.android.synthetic.main.partial_sign_in.*
import kotlinx.android.synthetic.main.partial_toolbar.*


class SignInTunerActivity : BaseDiMvpActivity<SignInComponent, SignInView, SignInPresenter>(), SignInView {

    /* inject */

    override fun createComponent(): SignInComponent {
        return SmartMirrorApp
                .activityComponentBuilders[SignInTunerActivity::class.java]?.build() as SignInComponent
    }

    override fun injectMembers(instance: SignInComponent) {
        instance.injectMembers(this)
    }


    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_tuner)
        setupToolbar()
        presenter.loadPreviousUserData()
        presenter.initLoginButtonStateListener(emailEdit.onTextChangedObservable(), passwordEdit.onTextChangedObservable())
        presenter.initEditableAction(passwordEdit.onEditorActionObservable())

        login.setOnClickListener { onLoginClicked() }
        createAccount.setOnClickListener {
            val intent = Intent(context, SignUpTunerActivity::class.java)
            startActivity(intent)
        }

        restorePassword.setOnClickListener { showDialogForrestPassword() }
    }

    override fun onResume() {
        super.onResume()
        presenter.onStartFetchAuth()
    }

    override fun onPause() {
        super.onPause()
        presenter.onStopFetchAuth()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    /* callbacks */

    override fun onPreviousTunerSessionLoaded(session: TunerSession) {
        emailEdit.setText(session.email)
    }

    override fun onEmailValidated(result: ValidationResult<String>) {
        if (!result.isValid) {
            email.error = getString(result.reasonStringRes)
        } else if (email.isErrorEnabled) {
            email.isErrorEnabled = false
        }
    }

    override fun onPasswordValidated(result: ValidationResult<String>) {
        if (!result.isValid) {
            password.error = getString(result.reasonStringRes)
        } else if (email.isErrorEnabled) {
            password.isErrorEnabled = false
        }
    }

    override fun onLoginSuccess() {
        ChooseMirrorActivity.start(context)
    }

    override fun setLoginButtonState(isEnabled: Boolean) {
        login.isEnabled = isEnabled
    }

    override fun onLoginClicked() {
        presenter.login(emailEdit.text.toString(), passwordEdit.text.toString())
    }

    override fun onPasswordReset() {
        AlertDialog.Builder(context)
                .setTitle(R.string.signIn_restore_password)
                .setMessage(R.string.dialog_success)
                .setPositiveButton(R.string.ok, null)
                .show()
    }


    /* private methods */

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
    }

    private fun showDialogForrestPassword(email: String? = null) {
        val editText = EditText(context)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        editText.layoutParams = layoutParams
        if (email != null) editText.setText(email, TextView.BufferType.EDITABLE);

        val dialog = AlertDialog.Builder(context)
                .setTitle(R.string.signIn_restore_password)
                .setView(editText)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, null)
                .create()

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (presenter.validateEmail(editText.text.toString())) {
                presenter.restorePassword(editText.text.toString())
                dialog.dismiss()
            } else {
                val snack = Snackbar.make(coordinatorLayout, R.string.error_invalid_email, LENGTH_LONG)
                val view = snack.view
                (view.findViewById(android.support.design.R.id.snackbar_text) as TextView).setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                snack.show()
            }
        }
    }
}