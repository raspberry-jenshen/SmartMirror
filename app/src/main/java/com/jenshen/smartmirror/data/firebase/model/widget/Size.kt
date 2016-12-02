package com.jenshen.smartmirror.data.firebase.model.widget

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.jenshen.smartmirror.data.firebase.FirebaseConstant

@IgnoreExtraProperties
data class Size(@set:PropertyName(FirebaseConstant.Widget.Size.WIDTH)
                @get:PropertyName(FirebaseConstant.Widget.Size.WIDTH)
                var width: Int = 0,
                @set:PropertyName(FirebaseConstant.Widget.Size.HEIGHT)
                @get:PropertyName(FirebaseConstant.Widget.Size.HEIGHT)
                var height: Int = 0) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Size> = object : Parcelable.Creator<Size> {
            override fun createFromParcel(source: Parcel): Size = Size(source)
            override fun newArray(size: Int): Array<Size?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(width)
        dest?.writeInt(height)
    }
}