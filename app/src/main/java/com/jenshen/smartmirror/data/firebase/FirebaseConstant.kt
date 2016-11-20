package com.jenshen.smartmirror.data.firebase

interface FirebaseConstant {

    companion object {
        const val MIRRORS: String = "mirrors"
        const val TUNERS: String = "tuners"
    }

    interface Mirrors {
        companion object {
            const val IS_WAITING_FOR_TUNER = "is_waiting_for_tuner"
        }
    }

    interface Tuners {
        companion object {
            const val MAC_ADDRESS = "mac_address"
            const val EMAIL = "email"
        }
    }
}