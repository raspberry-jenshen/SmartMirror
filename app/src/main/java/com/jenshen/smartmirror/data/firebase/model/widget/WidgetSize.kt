package com.jenshen.smartmirror.data.firebase.model.widget

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseRealTimeDatabaseConstant

@IgnoreExtraProperties
data class WidgetSize(@set:PropertyName(FirebaseRealTimeDatabaseConstant.Widget.Size.WIDTH)
                      @get:PropertyName(FirebaseRealTimeDatabaseConstant.Widget.Size.WIDTH)
                      var width: Int = 0,
                      @set:PropertyName(FirebaseRealTimeDatabaseConstant.Widget.Size.HEIGHT)
                      @get:PropertyName(FirebaseRealTimeDatabaseConstant.Widget.Size.HEIGHT)
                      var height: Int = 0) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<WidgetSize> = object : Parcelable.Creator<WidgetSize> {
            override fun createFromParcel(source: Parcel): WidgetSize = WidgetSize(source)
            override fun newArray(size: Int): Array<WidgetSize?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(width)
        dest?.writeInt(height)
    }
}