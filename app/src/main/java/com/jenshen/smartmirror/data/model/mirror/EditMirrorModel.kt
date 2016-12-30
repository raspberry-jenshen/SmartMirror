package com.jenshen.smartmirror.data.model.mirror

import android.os.Parcel
import android.os.Parcelable
import com.jenshen.smartmirror.data.model.widget.WidgetConfigurationModel

data class EditMirrorModel(val mirrorKey: String,
                           val columnsCount: Int,
                           val rowsCount: Int,
                           val title: String,
                           var isEnablePrecipitation: Boolean = false,
                           var configurationKey: String? = null,
                           var userInfoKey: String? = null,
                           val widgets: MutableList<WidgetConfigurationModel> = mutableListOf()) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<EditMirrorModel> = object : Parcelable.Creator<EditMirrorModel> {
            override fun createFromParcel(source: Parcel): EditMirrorModel = EditMirrorModel(source)
            override fun newArray(size: Int): Array<EditMirrorModel?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readInt(),
            source.readInt(), source.readString(),
            1 == source.readInt(), source.readString(), source.readString(),
            source.createTypedArrayList(WidgetConfigurationModel.CREATOR))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(mirrorKey)
        dest?.writeInt(columnsCount)
        dest?.writeInt(rowsCount)
        dest?.writeString(title)
        dest?.writeInt((if (isEnablePrecipitation) 1 else 0))
        dest?.writeString(configurationKey)
        dest?.writeString(userInfoKey)
        dest?.writeTypedList(widgets)
    }
}