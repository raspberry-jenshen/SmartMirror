package com.jenshen.smartmirror.data.model

import android.os.Parcel
import android.os.Parcelable

data class EditMirrorModel(val mirrorKey: String,
                           val title: String,
                           val list: MutableList<WidgetModel> = mutableListOf(),
                           var configurationKey: String? = null) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<EditMirrorModel> = object : Parcelable.Creator<EditMirrorModel> {
            override fun createFromParcel(source: Parcel): EditMirrorModel = EditMirrorModel(source)
            override fun newArray(size: Int): Array<EditMirrorModel?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(),
            source.readString(),
            source.createTypedArrayList(WidgetModel.CREATOR),
            source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(mirrorKey)
        dest?.writeString(title)
        dest?.writeString(configurationKey)
        dest?.writeTypedList(list)
    }
}