package com.jenshen.smartmirror.data.entity.session

import android.net.Uri

class TunerSession(id: String,
                   var email: String,
                   var nikeName: String?,
                   var avatar: Uri? = null) : Session(id)