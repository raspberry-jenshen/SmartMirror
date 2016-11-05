package com.jenshen.smartmirror.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User(@PrimaryKey var id: String = "",
                var email: String = "",
                var f_name: String = "",
                var l_name: String = "",
                var avatar: String? = null) : RealmObject()