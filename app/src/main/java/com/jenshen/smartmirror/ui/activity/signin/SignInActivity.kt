package com.jenshen.smartmirror.ui.activity.signIn

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.component.activity.signIn.SignInComponent
import com.jenshen.smartmirror.model.User
import com.jenshen.smartmirror.ui.activity.dashboard.tuner.TunerActivity
import com.jenshen.smartmirror.ui.activity.signup.SignUpActivity
import com.jenshen.smartmirror.ui.mvp.presenter.signin.SignInPresenter
import com.jenshen.smartmirror.ui.mvp.view.signIn.SignInView
import com.jenshen.smartmirror.util.reactive.onEditorAction
import com.jenshen.smartmirror.util.reactive.onTextChanged
import com.jenshen.smartmirror.util.validation.ValidationResult
import kotlinx.android.synthetic.main.partial_sign_in.*


class SignInActivity : BaseDiMvpActivity<SignInComponent, SignInView, SignInPresenter>(), SignInView {



    /* inject */

    override fun createComponent(): SignInComponent {
        return SmartMirrorApp
                .activityComponentBuilders[SignInActivity::class.java]?.build() as SignInComponent
    }

    override fun injectMembers(instance: SignInComponent) {
        instance.injectMembers(this)
    }


    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        presenter.loadPreviousUserData();
        presenter.initLoginButtonStateListener(emailEdit.onTextChanged(), passwordEdit.onTextChanged())
        presenter.initEditableAction(passwordEdit.onEditorAction())

        login.setOnClickListener { onLoginClicked() }
        createAccount.setOnClickListener {
            val intent = Intent(context, SignUpActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        restorePassword.setOnClickListener {
            val editText = EditText(context)
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            editText.layoutParams = layoutParams

            val dialog = AlertDialog.Builder(context)
                    .setTitle(R.string.login_restore_password)
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


    /* callbacks */

    override fun onUserPreviousLoaded(user: User?) {
        emailEdit.setText(user?.email)
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
        val intent = Intent(context, TunerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun setLoginButtonState(isEnabled: Boolean) {
        login.isEnabled = isEnabled
    }

    override fun onLoginClicked() {
        presenter.login(emailEdit.text.toString(), passwordEdit.text.toString())
    }
}