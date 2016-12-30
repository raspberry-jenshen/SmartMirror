package com.jenshen.smartmirror.manager.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.support.annotation.RequiresPermission
import android.support.v4.content.ContextCompat
import io.reactivex.Observable

interface IFindLocationManager {

    companion object {
        // The minimum distance to change Updates in meters
        private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 100 // 100 meters
        // The minimum time between updates in milliseconds
        private val MIN_TIME_BW_UPDATES = (1000 * 100).toLong() // 100 sec

        fun canGetLocation(context: Context) = isLocationPermissionEnabled(context) && isProvidersEnabled(context)

        fun isLocationPermissionEnabled(context: Context) = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        /**
         * Function to check GPS/wifi enabled

         * @return boolean
         */
        fun isProvidersEnabled(context: Context): Boolean {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
            // getting GPS status
            val isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER)

            // getting network status
            val isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            return isGPSEnabled || isNetworkEnabled
        }
    }

    fun removeOnLocationReceivedCallback(onLocationReceived: FindLocationManager.OnLocationReceived)
    fun addOnLocationReceivedCallback(onLocationReceived: FindLocationManager.OnLocationReceived)

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun loadLocation(minTimeToUpdate: Long = MIN_TIME_BW_UPDATES,
                     minDistanceToUpdate: Long = MIN_DISTANCE_CHANGE_FOR_UPDATES)

    fun stopUsingGPS()

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun fetchCurrentLocation(minTimeToUpdate: Long = MIN_TIME_BW_UPDATES,
                             minDistanceToUpdate: Long = MIN_DISTANCE_CHANGE_FOR_UPDATES): Observable<Location>

    fun canGetLocation(): Boolean
    fun getLongitude(): Double?
    fun getLatitude(): Double?
}