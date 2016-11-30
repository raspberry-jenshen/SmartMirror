package com.jenshen.smartmirror.data.model


import android.os.Parcel
import android.os.Parcelable
import com.jenshen.smartmirror.data.firebase.model.widget.Widget

data class WidgetModel(val id: String,
                       val widget: Widget) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<WidgetModel> = object : Parcelable.Creator<WidgetModel> {
            override fun createFromParcel(source: Parcel): WidgetModel = WidgetModel(source)
            override fun newArray(size: Int): Array<WidgetModel?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readParcelable<Widget>(Widget::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeParcelable(widget, 0)
    }
}
