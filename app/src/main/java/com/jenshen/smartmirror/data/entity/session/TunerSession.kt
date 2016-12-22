package com.jenshen.smartmirror.data.entity.session

class TunerSession(id: String,
                   var email: String,
                   var nikeName: String?,
                   var avatar: String? = null) : Session(id)