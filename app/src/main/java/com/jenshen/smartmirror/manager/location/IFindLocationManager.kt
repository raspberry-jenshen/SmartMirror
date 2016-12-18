package com.jenshen.smartmirror.manager.location

import android.Manifest
import android.location.Location
import android.support.annotation.RequiresPermission
import io.reactivex.Observable

interface IFindLocationManager {

    fun removeOnLocationReceivedCallback(onLocationReceived: FindLocationManager.OnLocationReceived)
    fun addOnLocationReceivedCallback(onLocationReceived: FindLocationManager.OnLocationReceived)

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun loadLocation()
    fun stopUsingGPS()

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun fetchCurrentLocation(): Observable<Location>

    fun canGetLocation(): Boolean
    fun getLongitude(): Double?
    fun getLatitude(): Double?
}