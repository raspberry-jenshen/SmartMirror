package com.jenshen.smartmirror.data.firebase

interface FirebaseConstant {

    companion object {
        const val MIRRORS: String = "mirrors"
        const val TUNERS: String = "tuners"
    }

    interface Mirror {
        companion object {
            const val IS_WAITING_FOR_TUNER = "is_waiting_for_tuner"
            const val SUBSCRIBERS = "subscribers"
        }

        interface MirrorSubscriber {
            companion object {
                const val ID = "id"
                const val LAST_TIME_UPDATE = "last_time_update"
            }
        }
    }

    interface Tuner {
        companion object {
            const val EMAIL = "email"
            const val SUBSCRIPTIONS = "subscriptions"
        }

        interface TunerSubscription {
            companion object {
                const val ID = "id"
            }
        }
    }
}