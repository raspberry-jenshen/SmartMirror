package com.jenshen.smartmirror.data.model.configuration

import com.jenshen.smartmirror.data.firebase.model.configuration.OrientationMode


data class ConfigurationSettingsModel(val configurationKay: String,
                                      val isUserInfoEnabled: Boolean = false,
                                      val isPrecipitationEnabled: Boolean = false,
                                      var orientationMode: OrientationMode = OrientationMode.PORTRAIT)