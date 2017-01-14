package com.jenshen.smartmirror.ui.mvp.presenter.service

import com.jenshen.compat.base.presenter.MvpRxPresenter
import com.jenshen.smartmirror.data.firebase.model.calendar.TypesOfUpdaters
import com.jenshen.smartmirror.interactor.calendar.ICalendarInteractor
import com.jenshen.smartmirror.ui.mvp.view.service.UpdateCalendarEventsView
import com.jenshen.smartmirror.util.reactive.applySchedulers
import io.reactivex.schedulers.Schedulers

class UpdateCalendarEventsPresenter constructor(private val calendarInteractor: ICalendarInteractor) :
        MvpRxPresenter<UpdateCalendarEventsView>() {

    fun onStartJob(@TypesOfUpdaters type: String) {
        calendarInteractor.updateEvents(type)
                .applySchedulers(Schedulers.io())
                .doOnSubscribe { compositeDisposable.add(it) }
                .subscribe({ view?.onJobCompleted() }, { view?.handleError(it) })
    }
}