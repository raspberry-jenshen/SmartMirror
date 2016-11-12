package com.jenshen.smartmirror.data.model

import android.os.Parcel
import android.os.Parcelable
import com.nguyenhoanglam.imagepicker.model.Image

data class UserModel(var email: String?,
                     var password: String?,
                     var name: String?,
                     var avatarImage: Image?) : Parcelable {

    constructor() : this(null, null, null, null)

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<UserModel> = object : Parcelable.Creator<UserModel> {
            override fun createFromParcel(source: Parcel): UserModel = UserModel(source)
            override fun newArray(size: Int): Array<UserModel?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readParcelable<Image>(Image::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(email)
        dest?.writeString(password)
        dest?.writeString(name)
        dest?.writeParcelable(avatarImage, 0)
    }
}