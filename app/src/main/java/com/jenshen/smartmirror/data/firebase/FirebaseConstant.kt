package com.jenshen.smartmirror.data.firebase

interface FirebaseConstant {

    companion object {
        val MIRRORS: String = "mirrors"
        val TUNERS: String = "tuners"
    }

    interface Mirrors {
        companion object {
            val MAC_ADDRESS = "mac_address"
        }
    }

    interface Tuners {
        companion object {
            val MAC_ADDRESS = "mac_address"
        }
    }
}