package com.jenshen.smartmirror.data.model


import android.os.Parcel
import android.os.Parcelable
import com.jenshen.smartmirror.data.firebase.model.widget.Widget
import com.jenshensoft.widgetview.entity.WidgetPosition

data class WidgetConfigurationModel(val widgetKey: String,
                                    val widget: Widget,
                                    var tag: String = widgetKey,
                                    var key: String? = null,
                                    var widgetPosition: WidgetPosition? = null) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<WidgetConfigurationModel> = object : Parcelable.Creator<WidgetConfigurationModel> {
            override fun createFromParcel(source: Parcel): WidgetConfigurationModel = WidgetConfigurationModel(source)
            override fun newArray(size: Int): Array<WidgetConfigurationModel?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readParcelable<Widget>(Widget::class.java.classLoader),
            source.readString(),
            source.readString(),
            source.readParcelable<WidgetPosition?>(WidgetPosition::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(widgetKey)
        dest?.writeParcelable(widget, 0)
        dest?.writeString(tag)
        dest?.writeString(key)
        dest?.writeParcelable(widgetPosition, 0)
    }
}
