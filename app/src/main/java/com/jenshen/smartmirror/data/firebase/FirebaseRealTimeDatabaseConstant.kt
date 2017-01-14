package com.jenshen.smartmirror.data.firebase

interface FirebaseRealTimeDatabaseConstant {

    companion object {
        const val MIRRORS: String = "mirrors"
        const val TUNERS: String = "tuners"
        const val WIDGETS: String = "widgets"
        const val USER_CALENDARS: String = "user_calendars"
        const val MIRROR_CONFIGURATIONS: String = "mirror_configurations"
    }

    interface MirrorConfiguration {
        companion object {
            const val MIRROR_KEY = "mirror_key"
            const val TITLE = "title"
            const val CONTAINER_SIZE = "container_size"
            const val WIDGETS = "widgets"
            const val USER_INFO_KEY = "user_info_key"
            const val ORIENTATION_MODE = "orientation_mode"
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
                const val TUNER_KEY = "tunerKey"
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
            const val CURRENT_WEATHER_WIDGET_KEY = "-KZEODMZZ-Bx87mu1JoR"
            const val WEATHER_FORECAST_FOR_DAY_WIDGET_KEY = "-KZLq6Oq9ydOvRYpgYOm"
            const val WEATHER_FORECAST_FOR_WEEK_WIDGET_KEY = "-KZTABib6OSjnBn79_8O"
            const val EXCHANGE_RATES_WIDGET_KEY = "-K_R8vRWtIZ4HkAWa93o"
            const val CALENDAR_EVENTS_WIDGET_KEY = "-Ka7tq0XcgoOXvFSOjSw"
            const val DIGITAL_CLOCK_WIDGET_KEY = "-KaGhfwH_uQbImz8w2u8"
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

    interface UserCalendar {
        companion object {
            const val EVENTS = "events"
            const val LAST_TIME_UPDATE = "last_time_update"
            const val TYPE_OF_UPDATER = "type_of_updater"
        }

        interface Event {
            companion object {
                const val ID = "id"
                const val TITLE = "title"
                const val CALENDAR_DISPLAY_NAME = "name"
                const val DESCRIPTION = "description"
                const val ALL_DAY = "isAllDay"
                const val DTSTART = "start_date"
                const val DTEND = "end_date"
                const val LAST_DATE = "last_date"
                const val EVENT_LOCATION = "event_location"
                const val CALENDAR_COLOR = "calendar_color"
                const val EVENT_COLOR = "event_color"
                const val ACCOUNT_NAME = "account_name"
            }
        }
    }
}