package com.jenshen.smartmirror.manager.calendar

import android.content.ContentResolver
import android.content.Context
import android.provider.CalendarContract
import android.support.annotation.RequiresPermission
import com.jenshen.smartmirror.data.firebase.model.calendar.CalendarEvent
import com.jenshen.smartmirror.data.firebase.model.calendar.UserCalendar
import com.jenshen.smartmirror.manager.firebase.database.RealtimeDatabaseManager
import com.jenshen.smartmirror.util.reactive.firebase.loadValue
import com.jenshen.smartmirror.util.reactive.firebase.uploadValue
import io.reactivex.*


class CalendarManager(context: Context, private val databaseManager: RealtimeDatabaseManager) : ICalendarManager {

    private val contentResolver: ContentResolver

    init {
        contentResolver = context.contentResolver
    }

    @RequiresPermission(android.Manifest.permission.READ_CALENDAR)
    override fun getEvents(currentTime: Long): Single<MutableList<CalendarEvent>> {
        return Single.create({ emitter ->
            val cursor = contentResolver.query(CalendarContract.Events.CONTENT_URI, FIELDS,
                    CalendarContract.Events.DTEND + " > " + currentTime,
                    null, CalendarContract.Events.DTSTART + " ASC" + " LIMIT 10")
            try {
                val list = mutableListOf<CalendarEvent>()
                if (cursor != null && cursor.count > 0) {
                    cursor.moveToFirst()
                    while (cursor.moveToNext() && !emitter.isDisposed) {
                       list.add(CalendarEvent(cursor.getLong(PROJECTION_ID_INDEX),
                                cursor.getString(PROJECTION_TITLE),
                                cursor.getString(PROJECTION_CALENDAR_DISPLAY_NAME),
                                cursor.getString(PROJECTION_DESCRIPTION),
                                cursor.getInt(PROJECTION_ALL_DAY) == 1,
                                cursor.getLong(PROJECTION_DTSTART),
                                cursor.getLong(PROJECTION_DTEND),
                                cursor.getLong(PROJECTION_LAST_DATE),
                                cursor.getString(PROJECTION_EVENT_LOCATION),
                                cursor.getInt(PROJECTION_CALENDAR_COLOR),
                                cursor.getInt(PROJECTION_EVENT_COLOR),
                                cursor.getString(PROJECTION_ACCOUNT_NAME)))
                    }
                }
                emitter.onSuccess(list)
            } catch (e: Exception) {
                emitter.onError(e)
            } finally {
                cursor?.close()
            }
            emitter.setCancellable {
                cursor?.close()
            }
        })
    }

    override fun getEvents(tunerKey: String): Maybe<UserCalendar> {
        return databaseManager.getUserCalendarRef(tunerKey)
                .flatMap { it.loadValue() }
                .filter { it.exists() }
                .map { it.getValue(UserCalendar::class.java) }
    }

    override fun setEvents(tunerKey: String, events: MutableList<CalendarEvent>): Completable {
        return databaseManager.getUserCalendarsRef()
                .map { it.child(tunerKey) }
                .flatMapCompletable { it.uploadValue(UserCalendar(events).toValueWithUpdateTime()) }
    }

    companion object {

        private val FIELDS = arrayOf(
                CalendarContract.Events._ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.CALENDAR_DISPLAY_NAME,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.ALL_DAY,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
                CalendarContract.Events.LAST_DATE,
                CalendarContract.Events.EVENT_LOCATION,
                CalendarContract.Events.CALENDAR_COLOR,
                CalendarContract.Events.EVENT_COLOR,
                CalendarContract.Events.ACCOUNT_NAME)

        // The indices for the projection array above.
        private val PROJECTION_ID_INDEX = 0
        private val PROJECTION_TITLE = 1
        private val PROJECTION_CALENDAR_DISPLAY_NAME = 2
        private val PROJECTION_DESCRIPTION = 3
        private val PROJECTION_ALL_DAY = 4
        private val PROJECTION_DTSTART = 5
        private val PROJECTION_DTEND = 6
        private val PROJECTION_LAST_DATE = 7
        private val PROJECTION_EVENT_LOCATION = 8
        private val PROJECTION_CALENDAR_COLOR = 9
        private val PROJECTION_EVENT_COLOR = 10
        private val PROJECTION_ACCOUNT_NAME = 11
    }
}
