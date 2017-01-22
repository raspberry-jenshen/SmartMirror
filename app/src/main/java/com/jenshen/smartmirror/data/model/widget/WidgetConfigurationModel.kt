package com.jenshen.smartmirror.data.model.widget


import android.os.Parcel
import android.os.Parcelable
import com.jenshen.smartmirror.data.firebase.model.widget.WidgetInfo
import com.jenshensoft.widgetview.entity.WidgetPosition

data class WidgetConfigurationModel(val widgetKey: WidgetKey,
                                    val widgetInfo: WidgetInfo,
                                    var tunerKey: String?, //for calendar
                                    var phrase: String?, //for phrase
                                    var key: String? = null,
                                    var widgetPosition: WidgetPosition? = null,
                                    var isDeleted: Boolean = false) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<WidgetConfigurationModel> = object : Parcelable.Creator<WidgetConfigurationModel> {
            override fun createFromParcel(source: Parcel): WidgetConfigurationModel = WidgetConfigurationModel(source)
            override fun newArray(size: Int): Array<WidgetConfigurationModel?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readParcelable<WidgetKey>(WidgetKey::class.java.classLoader),
            source.readParcelable<WidgetInfo>(WidgetInfo::class.java.classLoader),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readParcelable<WidgetPosition?>(WidgetPosition::class.java.classLoader), 1 == source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeParcelable(widgetKey, 0)
        dest?.writeParcelable(widgetInfo, 0)
        dest?.writeString(tunerKey)
        dest?.writeString(phrase)
        dest?.writeString(key)
        dest?.writeParcelable(widgetPosition, 0)
        dest?.writeInt((if (isDeleted) 1 else 0))
    }
}
