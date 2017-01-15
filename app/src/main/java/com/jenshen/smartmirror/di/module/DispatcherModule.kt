package com.jenshen.smartmirror.di.module

import android.content.Context
import com.firebase.jobdispatcher.*
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant
import com.jenshen.smartmirror.di.qualifier.CalendarJob
import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.service.UpdateCalendarEventsJobService
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit


@Module
class DispatcherModule {

    @SessionScope
    @Provides
    fun provideFirebaseJobDispatcher(context: Context): FirebaseJobDispatcher {
        return FirebaseJobDispatcher(GooglePlayDriver(context))
    }

    @CalendarJob
    @Provides
    fun provideCalendarEventsJob(dispatcher: FirebaseJobDispatcher): Job.Builder {
        // arguments for recurring job
        val periodicity = TimeUnit.HOURS.toSeconds(12).toInt()
        val toleranceInterval = TimeUnit.HOURS.toSeconds(1).toInt()

        return dispatcher.newJobBuilder()
                .setTag(FirebaseRealTimeDatabaseConstant.Widget.CALENDAR_EVENTS_WIDGET_KEY)
                .setService(UpdateCalendarEventsJobService::class.java)
                .setReplaceCurrent(true)// overwrite an existing job with the same tag
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)// persist past a device reboot
                .setTrigger(Trigger.executionWindow(periodicity, periodicity + toleranceInterval))
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)// retry with exponential backoff
                .setConstraints(// constraints that need to be satisfied for the job to run
                        // only run on an unmetered network
                        Constraint.ON_ANY_NETWORK/*,
                        // only run when the device is charging
                        Constraint.DEVICE_CHARGING*/
                )
    }
}