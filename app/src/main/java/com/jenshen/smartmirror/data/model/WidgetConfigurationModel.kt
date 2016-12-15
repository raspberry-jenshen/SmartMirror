package com.jenshen.smartmirror.data.model


import android.os.Parcel
import android.os.Parcelable
import com.jenshen.smartmirror.data.entity.widget.info.WidgetKey
import com.jenshen.smartmirror.data.firebase.model.widget.Widget
import com.jenshensoft.widgetview.entity.WidgetPosition

data class WidgetConfigurationModel(val widgetKey: WidgetKey,
                                    val widget: Widget,
                                    var key: String? = null,
                                    var widgetPosition: WidgetPosition? = null) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<WidgetConfigurationModel> = object : Parcelable.Creator<WidgetConfigurationModel> {
            override fun createFromParcel(source: Parcel): WidgetConfigurationModel = WidgetConfigurationModel(source)
            override fun newArray(size: Int): Array<WidgetConfigurationModel?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readParcelable<WidgetKey>(WidgetKey::class.java.classLoader),
            source.readParcelable<Widget>(Widget::class.java.classLoader),
            source.readString(),
            source.readParcelable<WidgetPosition?>(WidgetPosition::    class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeParcelable(widgetKey, 0)
        dest?.writeParcelable(widget, 0)
        dest?.writeString(key)
        dest?.writeParcelable(widgetPosition, 0)
    }
}
