package com.jenshen.smartmirror.ui.activity.login

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.component.activity.login.LoginComponent
import com.jenshen.smartmirror.ui.mvp.presenter.login.LoginPresenter
import com.jenshen.smartmirror.ui.mvp.view.login.LoginView
import com.jenshen.smartmirror.util.reactive.onEditorAction
import com.jenshen.smartmirror.util.reactive.onTextChanged
import com.jenshen.smartmirror.util.validation.ValidationResult
import kotlinx.android.synthetic.main.partial_login.*


class LoginActivity : BaseDiMvpActivity<LoginComponent, LoginView, LoginPresenter>(), LoginView {

    override fun onEmailValidated(result: ValidationResult<String>) {
        if (!result.isValid) {
            email.error = getString(result.reasonStringRes)
        } else if (email.isErrorEnabled) {
            email.isErrorEnabled = result.isValid
        }
    }

    override fun onPasswordValidated(result: ValidationResult<String>) {
        if (!result.isValid) {
            password.error = getString(result.reasonStringRes)
        } else if (email.isErrorEnabled) {
            password.isErrorEnabled = result.isValid
        }
    }

    override fun onLoginSuccess() {
       /* val intent = Intent(getContext(), DashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)*/
    }


    /* inject */

    override fun createComponent(): LoginComponent {
        return SmartMirrorApp
                .activityComponentBuilders[LoginActivity::class.java]?.build() as LoginComponent
    }

    override fun injectMembers(instance: LoginComponent) {
        instance.injectMembers(this)
    }


    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter.initLoginButtonStateListener(emailEdit.onTextChanged(), passwordEdit.onTextChanged())

        presenter.initEditableAction(passwordEdit.onEditorAction())

        login.setOnClickListener { onLoginClicked() }

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
                    Toast.makeText(context, R.string.login_error_invalid_email, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    /* callbacks */

    override fun setLoginButtonState(isEnabled: Boolean) {
        login.isEnabled = isEnabled
    }

    override fun onLoginClicked() {
        presenter.login(emailEdit.text.toString(), passwordEdit.text.toString())
    }
}