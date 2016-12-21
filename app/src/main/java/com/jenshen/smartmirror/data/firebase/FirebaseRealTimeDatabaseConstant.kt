package com.jenshen.smartmirror.data.firebase

interface FirebaseRealTimeDatabaseConstant {

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
            const val CONTAINER_SIZE = "container_size"
            const val WIDGETS = "widgets"
            const val USER_INFO_KEY = "user_info_key"
            const val IS_ENABLE_PRECIPITATION = "is_enable_precipitation"
        }

        interface ContainerSize {
            companion object {
                const val COLUMNS_COUNT = "columns_count"
                const val ROWS_COUNT = "rows_count"
            }
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
            const val TUNER_INFO = "tuner_info"
            const val SUBSCRIPTIONS = "subscriptions"
        }

        interface TunerInfo {
            companion object {
                const val NIKENAME = "nikename"
                const val EMAIL = "email"
                const val AVATAR_URL = "url"
            }
        }

        interface TunerSubscription {
            companion object {
                const val DEVICE_INFO = "device_info"
                const val LAST_TIME_UPDATE = "last_time_update"
            }
        }
    }
}