package com.jenshen.smartmirror.data.model.configuration


data class ConfigurationSettingsModel(val configurationKay: String,
                                      val isUserInfoEnabled: Boolean = false,
                                      val isPrecipitationEnabled: Boolean = false)