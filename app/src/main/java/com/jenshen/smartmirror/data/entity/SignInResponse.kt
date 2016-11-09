package com.jenshen.smartmirror.data.entity

import com.google.firebase.auth.FirebaseUser

data class SignInResponse(private val user: FirebaseUser)