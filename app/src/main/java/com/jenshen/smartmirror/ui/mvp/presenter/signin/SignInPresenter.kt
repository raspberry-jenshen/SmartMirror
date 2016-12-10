package com.jenshen.smartmirror.ui.mvp.presenter.signin

import android.view.inputmethod.EditorInfo
import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.entity.session.TunerSession
import com.jenshen.smartmirror.interactor.firebase.auth.FirebaseAuthInteractor
import com.jenshen.smartmirror.manager.preference.PreferencesManager
import com.jenshen.smartmirror.ui.mvp.view.signin.SignInView
import com.jenshen.smartmirror.util.reactive.applyProgress
import com.jenshen.smartmirror.util.reactive.applySchedulers
import com.jenshen.smartmirror.util.validation.isValidEmail
import com.jenshen.smartmirror.util.validation.isValidPassword
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SignInPresenter @Inject constructor(private val preferencesManager: PreferencesManager,
                                          private val authInteractor: FirebaseAuthInteractor) : MvpRxPresenter<SignInView>() {

    override fun attachView(view: SignInView?) {
        super.attachView(view)
        authInteractor.fetchAuth()
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onLoginSuccess() }, { view?.handleError(it) })
    }

    fun initLoginButtonStateListener(onTextChangedEmail: Observable<String>, onTextChangedPassword: Observable<String>) {
        Observable.combineLatest(onTextChangedEmail, onTextChangedPassword,
                BiFunction { email: String, password: String -> email.isNotEmpty() && password.isNotEmpty() })
                .distinctUntilChanged()
                .debounce(100, TimeUnit.MILLISECONDS)
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.setLoginButtonState(it) }, { view?.handleError(it) })
    }

    fun initEditableAction(editorAction: Observable<Int>) {
        editorAction
                .filter { it == EditorInfo.IME_ACTION_DONE }
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onLoginClicked() }, { view?.handleError(it) })
    }

    fun login(email: String, password: String) {
        //email
        val validateEmail = isValidEmail(email)
                .doOnSuccess { view?.onEmailValidated(it) }
                .map { it.isValid }
        //password
        val validatePassword = isValidPassword(email)
                .doOnSuccess { view?.onPasswordValidated(it) }
                .map { it.isValid }

        Single.zip(validateEmail, validatePassword, BiFunction { isValidEmail: Boolean, isValidPassword: Boolean -> isValidEmail && isValidPassword })
                .observeOn(Schedulers.io())
                .flatMapCompletable { isValid ->
                    if (isValid) {
                        authInteractor.signInTuner(email, password)
                    } else {
                        Completable.complete()
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .applyProgress(Consumer { view?.showProgress() }, Action { view?.hideProgress() })
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({}, { view?.handleError(it) })
    }

    fun validateEmail(email: String): Boolean {
        return com.jenshen.smartmirror.util.validation.validateEmail(email).isValid
    }

    fun loadPreviousUserData() {
        Maybe.fromCallable { preferencesManager.getSession() }
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .cast(TunerSession::class.java)
                .subscribe({ view?.onPreviousTunerSessionLoaded(it) }, { view?.handleError(it) })
    }

    fun restorePassword(email: String) {
        /*isValidEmail(email)
                .filter { it.isValid }
                .switchIfEmpty { Completable.complete() }
                .doOnSuccess { view?.showProgress() }
                .observeOn(Schedulers.io())
                .flatMapObservable { loginApiManager.restorePassword(RestorePasswordRequest(email)) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { composite.add(it) }
                .subscribe({
                    view?.onPasswordRestoreSuccess()
                    view?.hideProgress()
                }, {
                    errorHandler.handleError(view?.getContext(), it)
                    view?.hideProgress()
                })*/
    }
}