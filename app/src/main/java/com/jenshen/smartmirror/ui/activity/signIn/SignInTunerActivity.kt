package com.jenshen.smartmirror.ui.activity.signIn

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.di.component.activity.signIn.SignInComponent
import com.jenshen.smartmirror.ui.activity.choose.mirror.ChooseMirrorActivity
import com.jenshen.smartmirror.ui.activity.signup.tuner.SignUpTunerActivity
import com.jenshen.smartmirror.ui.mvp.presenter.signin.SignInPresenter
import com.jenshen.smartmirror.ui.mvp.view.signIn.SignInView
import com.jenshen.smartmirror.util.reactive.onEditorAction
import com.jenshen.smartmirror.util.reactive.onTextChanged
import com.jenshen.smartmirror.util.validation.ValidationResult
import kotlinx.android.synthetic.main.activity_sign_up_tuner.*
import kotlinx.android.synthetic.main.partial_sign_in.*


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
        presenter.initLoginButtonStateListener(emailEdit.onTextChanged(), passwordEdit.onTextChanged())
        presenter.initEditableAction(passwordEdit.onEditorAction())

        login.setOnClickListener { onLoginClicked() }
        createAccount.setOnClickListener {
            val intent = Intent(context, SignUpTunerActivity::class.java)
            startActivity(intent)
        }

        restorePassword.setOnClickListener {
            val editText = EditText(context)
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            editText.layoutParams = layoutParams

            val dialog = AlertDialog.Builder(context)
                    .setTitle(R.string.signIn_restore_password)
                    .setView(editText)
                    .setPositiveButton(R.string.ok, { dialogInterface, i -> })
                    .setNegativeButton(R.string.cancel, { dialogInterface, i -> dialogInterface.dismiss() })
                    .create()

            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (presenter.validateEmail(editText.text.toString())) {
                    presenter.restorePassword(editText.text.toString())
                    dialog.dismiss()
                } else {
                    Toast.makeText(context, R.string.error_invalid_email, Toast.LENGTH_LONG).show()
                }
            }
        }
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
        val intent = Intent(context, ChooseMirrorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun setLoginButtonState(isEnabled: Boolean) {
        login.isEnabled = isEnabled
    }

    override fun onLoginClicked() {
        presenter.login(emailEdit.text.toString(), passwordEdit.text.toString())
    }


    /* private methods */

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
    }
}