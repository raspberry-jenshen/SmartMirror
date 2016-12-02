package com.jenshen.smartmirror.data.firebase.model.widget

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant


@IgnoreExtraProperties
data class Widget(@set:PropertyName(FirebaseConstant.Widget.NAME)
                  @get:PropertyName(FirebaseConstant.Widget.NAME)
                  var name: String = "widget",
                  @set:PropertyName(FirebaseConstant.Widget.DEFAULT_SIZE)
                  @get:PropertyName(FirebaseConstant.Widget.DEFAULT_SIZE)
                  var defaultSize: Size = Size()) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Widget> = object : Parcelable.Creator<Widget> {
            override fun createFromParcel(source: Parcel): Widget = Widget(source)
            override fun newArray(size: Int): Array<Widget?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readParcelable<Size>(Size::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeParcelable(defaultSize, 0)
    }
}
