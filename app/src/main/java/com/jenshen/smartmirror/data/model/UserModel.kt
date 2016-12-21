package com.jenshen.smartmirror.data.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class UserModel(var email: String? = null,
                     var name: String? = null,
                     var avatarImage: Uri? = null) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<UserModel> = object : Parcelable.Creator<UserModel> {
            override fun createFromParcel(source: Parcel): UserModel = UserModel(source)
            override fun newArray(size: Int): Array<UserModel?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readParcelable<Uri?>(Uri::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(email)
        dest?.writeString(name)
        dest?.writeParcelable(avatarImage, 0)
    }
}