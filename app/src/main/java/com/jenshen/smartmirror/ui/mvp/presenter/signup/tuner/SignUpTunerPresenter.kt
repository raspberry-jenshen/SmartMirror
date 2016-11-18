package com.jenshen.smartmirror.ui.mvp.presenter.signup.tuner

import android.view.inputmethod.EditorInfo
import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.interactor.firebase.api.FirebaseApiInteractor
import com.jenshen.smartmirror.interactor.firebase.auth.FirebaseAuthInteractor
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.ui.mvp.view.signup.tuner.SignUpTunerView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import com.jenshen.smartmirror.util.validation.isValidConfirmPassword
import com.jenshen.smartmirror.util.validation.isValidEmail
import com.jenshen.smartmirror.util.validation.isValidPassword
import com.jenshen.smartmirror.util.validation.isValidUserName
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SignUpTunerPresenter @Inject constructor(private val apiInteractor: FirebaseApiInteractor,
                                               private val preferencesManager: PreferencesManager,
                                               private val authInteractor: FirebaseAuthInteractor) : MvpRxPresenter<SignUpTunerView>() {

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

    fun createAccount(name: String, email: String, password: String, confirmPassword: String) {
        //name
        val validateName = isValidUserName(name)
                .doOnSuccess { view?.onUsernameValidated(it) }
                .map { it.isValid }
        //email
        val validateEmail = isValidEmail(email)
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
                Function4 { isValidUserName: Boolean, isValidEmail: Boolean, isValidPassword: Boolean, isValidConfirmPassword: Boolean ->
                    isValidUserName && isValidEmail && isValidPassword && isValidConfirmPassword
                })
                .doOnSuccess {
                    view?.showProgress()
                    isTaskFinished = false
                }
                .observeOn(Schedulers.io())
                .flatMapCompletable { isValid ->
                    if (isValid) {
                        authInteractor.createNewTuner(email, password)
                    } else {
                        Completable.complete()
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({
                    if (isTaskFinished) {
                        view?.hideProgress()
                    }
                }, {
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
        if (isTaskFinished) {
            view?.showProgress()
            isTaskFinished = false
        }
        Single.fromCallable { preferencesManager.getSession()!! }
                .cast(TunerSession::class.java)
                .flatMap { apiInteractor.createOrGetTuner(it) }
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({
                    view?.onCreateAccountSuccess()
                    view?.hideProgress()
                    isTaskFinished = true
                }, {
                    view?.handleError(it)
                    view?.hideProgress()
                    isTaskFinished = true
                })
    }
}