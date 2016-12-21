package com.jenshen.smartmirror.ui.mvp.presenter.signup.tuner

import android.net.Uri
import android.view.inputmethod.EditorInfo
import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.data.model.UserModel
import com.jenshen.smartmirror.interactor.firebase.api.FirebaseApiInteractor
import com.jenshen.smartmirror.interactor.firebase.auth.FirebaseAuthInteractor
import com.jenshen.smartmirror.interactor.firebase.storage.IStorageInteractor
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.ui.mvp.view.signup.tuner.SignUpTunerView
import com.jenshen.smartmirror.util.reactive.applyProgress
import com.jenshen.smartmirror.util.reactive.applySchedulers
import com.jenshen.smartmirror.util.validation.isValidConfirmPassword
import com.jenshen.smartmirror.util.validation.isValidEmail
import com.jenshen.smartmirror.util.validation.isValidPassword
import com.jenshen.smartmirror.util.validation.isValidUserName
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SignUpTunerPresenter @Inject constructor(private val apiInteractor: FirebaseApiInteractor,
                                               private val preferencesManager: PreferencesManager,
                                               private val storageInteractor: IStorageInteractor,
                                               private val authInteractor: FirebaseAuthInteractor) : MvpRxPresenter<SignUpTunerView>() {

    private var userModel: UserModel? = null
    private var isTaskFinished = true

    override fun attachView(view: SignUpTunerView?) {
        super.attachView(view)
        authInteractor.fetchAuth()
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ onCreateTunerAccount() }, { view?.handleError(it) })
    }

    fun initCreateAccountButtonStateListener(nameEdit: Observable<String>,
                                             emailEdit: Observable<String>,
                                             passwordEdit: Observable<String>,
                                             confirmPasswordEdit: Observable<String>) {
        Observable.combineLatest(nameEdit, emailEdit, passwordEdit, confirmPasswordEdit,
                Function4 { name: String, email: String, password: String, confirmPassword: String ->
                    name.isNotEmpty() &&
                            email.isNotEmpty() &&
                            password.isNotEmpty() &&
                            confirmPassword.isNotEmpty()
                })
                .distinctUntilChanged()
                .debounce(100, TimeUnit.MILLISECONDS)
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.setCreateAccountButtonState(it) }, { view?.handleError(it) })
    }

    fun createAccount(password: String, confirmPassword: String, userModel: UserModel) {
        //name
        val validateName = isValidUserName(userModel.name!!)
                .doOnSuccess { view?.onUsernameValidated(it) }
                .map { it.isValid }
        //email
        val validateEmail = isValidEmail(userModel.email!!)
                .doOnSuccess { view?.onEmailValidated(it) }
                .map { it.isValid }
        //password
        val validatePassword = isValidPassword(password)
                .doOnSuccess { view?.onPasswordValidated(it) }
                .map { it.isValid }
        //confirm password
        val validateConfirmPassword = isValidConfirmPassword(password, confirmPassword)
                .doOnSuccess { view?.onConfirmPasswordValidated(it) }
                .map { it.isValid }

        Single.zip(validateName, validateEmail, validatePassword, validateConfirmPassword,
                Function4 { isValidUserName: Boolean,
                            isValidEmail: Boolean,
                            isValidPassword: Boolean,
                            isValidConfirmPassword: Boolean ->
                    isValidUserName && isValidEmail && isValidPassword && isValidConfirmPassword
                })
                .observeOn(Schedulers.io())
                .flatMapCompletable { isValid ->
                    if (isValid) {
                        this.userModel = userModel
                        authInteractor.createNewTuner(userModel.email!!, password)
                    } else {
                        isTaskFinished = true
                        Completable.complete()
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .applyProgress(Consumer {
                    view?.showProgress()
                    isTaskFinished = false
                }, Action {
                    if (isTaskFinished) {
                        view?.hideProgress()
                    }
                })
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({}, {
                    view?.handleError(it)
                    view?.hideProgress()
                    isTaskFinished = true
                })
    }

    fun initEditableAction(editorAction: Observable<Int>) {
        editorAction
                .filter { it == EditorInfo.IME_ACTION_DONE }
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onCreateAccountClicked() }, { view?.handleError(it) })
    }

    fun onCreateTunerAccount() {
        updateUserSession(userModel)
                .flatMap { apiInteractor.createOrGetTuner(it) }
                .flatMapCompletable { authInteractor.editUserInfo(it.tunerInfo.nikeName, Uri.parse(it.tunerInfo.avatarUrl)) }
                .applySchedulers(Schedulers.io())
                .applyProgress(Consumer {
                    if (isTaskFinished) {
                        view?.showProgress()
                        isTaskFinished = false
                    }
                }, Action {
                    view?.hideProgress()
                    isTaskFinished = true
                })
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({
                    view?.onCreateAccountSuccess()
                }, {
                    view?.handleError(it)
                })
    }

    /* private methods */

    private fun updateUserSession(userModel: UserModel?): Single<TunerSession> {
        return Single.fromCallable { preferencesManager.getSession()!! }
                .cast(TunerSession::class.java)
                .flatMap { tunerSession ->
                    if (userModel != null) {
                        val singleUserModule: Single<TunerSession>
                        if (userModel.avatarImage != null) {
                            singleUserModule = storageInteractor.uploadImage(tunerSession.key, userModel.avatarImage!!)
                                    .map {
                                        tunerSession.avatar = it
                                        return@map tunerSession
                                    }
                        } else {
                            singleUserModule = Single.fromCallable { tunerSession }
                        }
                        return@flatMap singleUserModule
                                .doOnSuccess {
                                    it.email = userModel.email!!
                                    it.avatar = userModel.avatarImage
                                    it.nikeName = userModel.name
                                }
                                .doOnSuccess { preferencesManager.sighIn(it, false) }
                    } else {
                        return@flatMap Single.fromCallable { tunerSession }
                    }
                }
    }
}