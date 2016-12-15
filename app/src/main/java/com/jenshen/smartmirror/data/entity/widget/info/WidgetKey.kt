package com.jenshen.smartmirror.data.entity.widget.info

import android.os.Parcel
import android.os.Parcelable


data class WidgetKey(val key: String, var number: Int) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<WidgetKey> = object : Parcelable.Creator<WidgetKey> {
            override fun createFromParcel(source: Parcel): WidgetKey = WidgetKey(source)
            override fun newArray(size: Int): Array<WidgetKey?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(key)
        dest?.writeInt(number)
    }
}
