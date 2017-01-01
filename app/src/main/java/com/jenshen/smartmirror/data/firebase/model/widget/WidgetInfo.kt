package com.jenshen.smartmirror.data.firebase.model.widget

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant


@IgnoreExtraProperties
data class WidgetInfo(@set:PropertyName(FirebaseRealTimeDatabaseConstant.Widget.NAME)
                      @get:PropertyName(FirebaseRealTimeDatabaseConstant.Widget.NAME)
                      var name: String = "widget",
                      @set:PropertyName(FirebaseRealTimeDatabaseConstant.Widget.DEFAULT_SIZE)
                      @get:PropertyName(FirebaseRealTimeDatabaseConstant.Widget.DEFAULT_SIZE)
                      var defaultSize: WidgetSize = WidgetSize()) : Parcelable {

    companion object {

        const val CLOCK_WIDGET_KEY = "-KXmMIRVjA3K4zSPwsYv"
        const val CURRENT_WEATHER_WIDGET_KEY = "-KZEODMZZ-Bx87mu1JoR"
        const val WEATHER_FORECAST_FOR_DAY_WIDGET_KEY = "-KZLq6Oq9ydOvRYpgYOm"
        const val WEATHER_FORECAST_FOR_WEEK_WIDGET_KEY = "-KZTABib6OSjnBn79_8O"
        const val EXCHANGE_RATES_WIDGET_KEY = "-K_R8vRWtIZ4HkAWa93o"

        @JvmField val CREATOR: Parcelable.Creator<WidgetInfo> = object : Parcelable.Creator<WidgetInfo> {
            override fun createFromParcel(source: Parcel): WidgetInfo = WidgetInfo(source)
            override fun newArray(size: Int): Array<WidgetInfo?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readParcelable<WidgetSize>(WidgetSize::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeParcelable(defaultSize, 0)
    }
}
