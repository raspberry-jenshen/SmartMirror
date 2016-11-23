package com.jenshen.smartmirror.ui.activity.signup.tuner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import com.bumptech.glide.Glide
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.data.model.UserModel
import com.jenshen.smartmirror.di.component.activity.signUp.tuner.SignUpTunerComponent
import com.jenshen.smartmirror.ui.activity.choose.mirror.ChooseMirrorActivity
import com.jenshen.smartmirror.ui.mvp.presenter.signup.tuner.SignUpTunerPresenter
import com.jenshen.smartmirror.ui.mvp.view.signup.tuner.SignUpTunerView
import com.jenshen.smartmirror.util.asCircleBitmap
import com.jenshen.smartmirror.util.getBitmap
import com.jenshen.smartmirror.util.reactive.onEditorAction
import com.jenshen.smartmirror.util.reactive.onTextChanged
import com.jenshen.smartmirror.util.validation.ValidationResult
import com.nguyenhoanglam.imagepicker.activity.ImagePicker
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES
import com.nguyenhoanglam.imagepicker.model.Image
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_sign_up_tuner.*
import kotlinx.android.synthetic.main.partial_sign_up.*
import java.io.File


class SignUpTunerActivity : BaseDiMvpActivity<SignUpTunerComponent, SignUpTunerView, SignUpTunerPresenter>(), SignUpTunerView {

    companion object {
        const val KEY_USER_MODEL = "KEY_USER_MODEL"
        const val REQUEST_CODE_PICKER = 0
    }

    private lateinit var userModel: UserModel

    /* inject */

    override fun createComponent(): SignUpTunerComponent {
        return SmartMirrorApp
                .activityComponentBuilders[SignUpTunerActivity::class.java]?.build() as SignUpTunerComponent
    }

    override fun injectMembers(instance: SignUpTunerComponent) {
        instance.injectMembers(this)
    }

    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_tuner)
        setupToolbar()
        userModel = savedInstanceState?.getParcelable(KEY_USER_MODEL) ?: UserModel()
        loadAvatar(userModel.avatarImage)

        avatar.setOnClickListener {
            ImagePicker.create(this)
                    .folderMode(true) // folder mode (false by default)
                    .folderTitle(getString(R.string.signUp_choose_photo)) // folder selection title
                    .imageTitle(getString(R.string.signUp_Tap_to_select)) // image selection title
                    .single() // single mode
                    .showCamera(true) // show camera or not (true by default)
                    .start(REQUEST_CODE_PICKER)
        }

        createAccount.setOnClickListener {
            onCreateAccountClicked()
        }

        presenter.initCreateAccountButtonStateListener(
                nameEdit.onTextChanged(),
                emailEdit.onTextChanged(),
                passwordEdit.onTextChanged(),
                confirmPasswordEdit.onTextChanged())
        presenter.initEditableAction(confirmPasswordEdit.onEditorAction())
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(KEY_USER_MODEL, userModel)
    }

    /* callbacks */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == Activity.RESULT_OK && data != null) {
            val images = data.getParcelableArrayListExtra<Parcelable>(INTENT_EXTRA_SELECTED_IMAGES)
            val iterator = images.iterator()
            if (iterator.hasNext()) {
                val image = iterator.next() as Image
                userModel.avatarImage = image
                loadAvatar(image)
            } else {
                Log.e("SmartMirror", "Can't load an image")
            }
        }
    }

    override fun onUsernameValidated(result: ValidationResult<String>) {
        if (!result.isValid) {
            name.error = getString(result.reasonStringRes)
            email.isErrorEnabled = !result.isValid
        } else if (email.isErrorEnabled) {
            name.isErrorEnabled = false
        }
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

    override fun onConfirmPasswordValidated(result: ValidationResult<String>) {
        if (!result.isValid) {
            confirmPassword.error = getString(result.reasonStringRes)
        } else if (email.isErrorEnabled) {
            confirmPassword.isErrorEnabled = false
        }
    }

    override fun setCreateAccountButtonState(isEnabled: Boolean) {
        createAccount.isEnabled = true
    }

    override fun onCreateAccountClicked() {
        presenter.createAccount(
                nameEdit.text.toString().trim(),
                emailEdit.text.toString(),
                passwordEdit.text.toString(),
                confirmPasswordEdit.text.toString())
    }

    override fun onCreateAccountSuccess() {
        val intent = Intent(context, ChooseMirrorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    /* private methods */

    private fun loadAvatar(image: Image?) {
        if (image == null) {
            avatar.setImageBitmap(getBitmap(context, R.drawable.ic_demo_avatar).asCircleBitmap())
        } else {
            Glide.with(context)
                    .load(File(image.path))
                    .bitmapTransform(CropCircleTransformation(context))
                    .into(avatar)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
    }
}