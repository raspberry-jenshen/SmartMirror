package com.jenshen.smartmirror.data.firebase

interface FirebaseConstant {

    companion object {
        const val MIRRORS: String = "mirrors"
        const val TUNERS: String = "tuners"
        const val WIDGETS: String = "widgets"
        const val MIRROR_CONFIGURATIONS: String = "mirror_configurations"
    }

    interface MirrorConfiguration {
        companion object {
            const val MIRROR_KEY = "mirror_key"
            const val TITLE = "title"
            const val WIDGETS = "widgets"
            const val LAST_TIME_UPDATE = "last_time_update"
        }

        interface Widget {
            companion object {
                const val WIDGET_KEY = "widgetKey"
                const val TOP_LEFT_CORNER = "topLeft_corner"
                const val TOP_RIGHT_CORNER = "topRight_corner"
                const val BOTTOM_LEFT_CORNER = "bottomLeft_corner"
                const val BOTTOM_RIGHT_CORNER = "bottomRight_corner"
            }

            interface Corner {
                companion object {
                    const val COLUMN = "column"
                    const val ROW = "row"
                }
            }
        }
    }

    interface Widget {
        companion object {
            const val NAME = "name"
            const val DEFAULT_SIZE = "default_size"

            const val CLOCK_WIDGET_KEY = "-KXmMIRVjA3K4zSPwsYv"
        }

        interface Size {
            companion object {
                const val WIDTH = "width"
                const val HEIGHT = "height"
            }
        }
    }

    interface Mirror {
        companion object {
            const val DEVICE_INFO = "device_info"
            const val SELECTED_CONFIGURATION_ID = "selected_configuration_key"
            const val IS_WAITING_FOR_TUNER = "is_waiting_for_tuner"
            const val SUBSCRIBERS = "subscribers"
            const val CONFIGURATIONS = "configurations"
        }

        interface MirrorSubscriber {
            companion object {
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
                const val DEVICE_INFO = "device_info"
                const val LAST_TIME_UPDATE = "last_time_update"
            }
        }
    }
}