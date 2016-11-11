package com.jenshen.smartmirror.ui.activity.signUp

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.app.AlertDialog
import com.jenshen.compat.base.view.impl.mvp.lce.component.BaseDiMvpActivity
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.di.component.activity.signUp.SignUpComponent
import com.jenshen.smartmirror.manager.photo.IPhotoManager
import com.jenshen.smartmirror.manager.photo.IPhotoManager.PhotoMode.PHOTO_FROM_CAMERA
import com.jenshen.smartmirror.manager.photo.IPhotoManager.PhotoMode.PHOTO_FROM_GALLERY
import com.jenshen.smartmirror.ui.mvp.presenter.signUp.SignUpPresenter
import com.jenshen.smartmirror.ui.mvp.view.signUp.SignUpView
import com.jenshen.smartmirror.util.reactive.onTextChanged
import com.jenshen.smartmirror.util.validation.ValidationResult
import kotlinx.android.synthetic.main.partial_sign_up.*
import java.util.*
import javax.inject.Inject


class SignUpActivity : BaseDiMvpActivity<SignUpComponent, SignUpView, SignUpPresenter>(), SignUpView {

    companion object {
        const val KEY_USER_MODEL: String = "KEY_USER_MODEL"
    }

    @Inject
     lateinit var photoManager : IPhotoManager

    private lateinit var userModel: UserModel

    /* inject */

    override fun createComponent(): SignUpComponent {
        return SmartMirrorApp
                .activityComponentBuilders[SignUpActivity::class.java]?.build() as SignUpComponent
    }

    override fun injectMembers(instance: SignUpComponent) {
        instance.injectMembers(this)
    }

    /* lifecycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        userModel = savedInstanceState?.getParcelable(KEY_USER_MODEL) ?: UserModel(null, null, null)

        addAvatar.setOnClickListener { showChoosePhotoAlertDialog() }

        createAccount.setOnClickListener {
            presenter.createAccount(
                    nameEdit.text.toString(),
                    emailEdit.text.toString(),
                    passwordEdit.text.toString(),
                    confirmPasswordEdit.text.toString())
        }

        presenter.initCreateAccountButtonStateListener(
                nameEdit.onTextChanged(),
                emailEdit.onTextChanged(),
                passwordEdit.onTextChanged(),
                confirmPasswordEdit.onTextChanged())
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(KEY_USER_MODEL, userModel)
    }

    /* callbacks */

    override fun onUsernameValidated(result: ValidationResult<String>) {
        if (!result.isValid) {
            name.error = getString(result.reasonStringRes)
        } else if (email.isErrorEnabled) {
            name.isErrorEnabled = result.isValid
        }
    }

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

    override fun onConfirmPasswordValidated(result: ValidationResult<String>) {
        if (!result.isValid) {
            confirmPassword.error = getString(result.reasonStringRes)
        } else if (email.isErrorEnabled) {
            confirmPassword.isErrorEnabled = result.isValid
        }
    }

    override fun setCreateAccountButtonState(isEnabled: Boolean) {
        createAccount.isEnabled = isEnabled
    }

    data class UserModel(var email: String?,
                         var password: String?,
                         var name: String?,
                         var avatarUrl: String = "http://www.lovemarks.com/wp-content/uploads/profile-avatars/default-avatar-plaid-shirt-guy.png") : Parcelable {
        companion object {
            @JvmField val CREATOR: Parcelable.Creator<UserModel> = object : Parcelable.Creator<UserModel> {
                override fun createFromParcel(source: Parcel): UserModel = UserModel(source)
                override fun newArray(size: Int): Array<UserModel?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readString())

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeString(email)
            dest?.writeString(password)
            dest?.writeString(name)
            dest?.writeString(avatarUrl)
        }
    }

    fun showChoosePhotoAlertDialog() {
        val ACTION_CAMERA = 0
        val ACTION_GALLERY = 1
        val actions = ArrayList<String>()

        actions.add(getString(R.string.signUp_from_camera))
        actions.add(getString(R.string.signUp_from_gallery))

        AlertDialog.Builder(context)
                .setTitle(getString(R.string.signUp_choose_photo))
                .setItems(actions.toTypedArray()) { dialog, which ->
                    when (which) {
                        ACTION_CAMERA -> photoManager.takePhoto(PHOTO_FROM_CAMERA)
                        ACTION_GALLERY -> photoManager.takePhoto(PHOTO_FROM_GALLERY)
                    }
                }.create().show()
    }
}