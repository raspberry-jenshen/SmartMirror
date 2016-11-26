package com.jenshen.smartmirror.data.firebase

interface FirebaseConstant {

    companion object {
        const val MIRRORS: String = "mirrors"
        const val TUNERS: String = "tuners"
    }

    interface MirrorConfiguration {
        companion object {
            const val MIRROR_ID = "mirror_id"
            const val LAST_TIME_UPDATE = "last_time_update"
        }
    }

    interface Mirror {
        companion object {
            const val DEVICE_INFO = "device_info"
            const val SELECTED_CONFIGURATION_ID = "selected_configuration_id"
            const val IS_WAITING_FOR_TUNER = "is_waiting_for_tuner"
            const val SUBSCRIBERS = "subscribers"
            const val CONFIGURATIONS = "configurations"
        }

        interface MirrorSubscriber {
            companion object {
                const val ID = "id"
                const val LAST_TIME_UPDATE = "last_time_update"
            }
        }

        interface MirrorConfigurationInfo {
            companion object {
                const val TITLE = "title"
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
                const val DEVICE_INFO = "device_info"
            }
        }
    }
}