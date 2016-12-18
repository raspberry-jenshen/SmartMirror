package com.jenshen.smartmirror.data.firebase.model.widget

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant


@IgnoreExtraProperties
data class WidgetInfo(@set:PropertyName(FirebaseConstant.Widget.NAME)
                      @get:PropertyName(FirebaseConstant.Widget.NAME)
                      var name: String = "widget",
                      @set:PropertyName(FirebaseConstant.Widget.DEFAULT_SIZE)
                      @get:PropertyName(FirebaseConstant.Widget.DEFAULT_SIZE)
                      var defaultSize: WidgetSize = WidgetSize()) : Parcelable {

    companion object {

        const val CLOCK_WIDGET_KEY = "-KXmMIRVjA3K4zSPwsYv"
        const val WEATHER_WIDGET_KEY = "-KZEODMZZ-Bx87mu1JoR"

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
