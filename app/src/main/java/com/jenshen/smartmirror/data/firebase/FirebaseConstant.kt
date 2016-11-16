package com.jenshen.smartmirror.data.firebase

interface FirebaseConstant {

    companion object {
        val MIRRORS: String = "mirrors";
    }

    interface Mirrors {
        companion object {
            val ID = "id"
            val MAC_ADDRESS = "mac_address";
        }
    }
}