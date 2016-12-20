package com.jenshen.smartmirror.manager.location

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.annotation.RequiresPermission
import io.reactivex.Observable

class FindLocationManager(context: Context) : LocationListener, IFindLocationManager {

    private val locationManager: LocationManager
    private val onLocationReceivedCallbacks: MutableList<OnLocationReceived>
    // flag for GPS status
    private var canGetLocation = false

    private var location: Location? = null
    private var latitude: Double? = null
    private var longitude: Double? = null

    init {
        this.locationManager = context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
        this.onLocationReceivedCallbacks = mutableListOf()
    }

    companion object {

        // The minimum distance to change Updates in meters
        private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 100 // 100 meters
        // The minimum time between updates in milliseconds
        private val MIN_TIME_BW_UPDATES = (1000 * 100).toLong() // 100 sec
    }

    override fun addOnLocationReceivedCallback(onLocationReceived: OnLocationReceived) {
        this.onLocationReceivedCallbacks.add(onLocationReceived)
        sendLocation()
    }

    override fun removeOnLocationReceivedCallback(onLocationReceived: OnLocationReceived) {
        this.onLocationReceivedCallbacks.remove(onLocationReceived)
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun fetchCurrentLocation(): Observable<Location> {
       return Observable.create { source ->
            val onLocationReceived: OnLocationReceived = object : OnLocationReceived {
                override fun onReceived(location: Location) {
                    source.onNext(location)
                }
            }
            addOnLocationReceivedCallback(onLocationReceived)
            loadLocation()
            source.setCancellable {
                removeOnLocationReceivedCallback(onLocationReceived)
                stopUsingGPS()
            }
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun loadLocation() {

        // getting GPS status
        val isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)

        // getting network status
        val isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isNetworkEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)

            location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (location != null) {
                latitude = location!!.latitude
                longitude = location!!.longitude

            }
        }

        if (isGPSEnabled) {
            if (location == null) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)

                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null) {
                    latitude = location!!.latitude
                    longitude = location!!.longitude

                }
            }
        }

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        } else {
            this.canGetLocation = true
        }

        sendLocation()
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    override fun stopUsingGPS() {
        locationManager.removeUpdates(this@FindLocationManager)
    }

    /**
     * Function to get latitude
     */
    override fun getLatitude(): Double? {
        if (location != null) {
            latitude = location!!.latitude
        }

        // return latitude
        return latitude
    }

    /**
     * Function to get longitude
     */
    override fun getLongitude(): Double? {
        if (location != null) {
            longitude = location!!.longitude
        }

        // return longitude
        return longitude
    }

    /**
     * Function to check GPS/wifi enabled

     * @return boolean
     */
    override fun canGetLocation(): Boolean {
        return this.canGetLocation
    }

    override fun onLocationChanged(location: Location) {
        this.location = location
        sendLocation()
    }

    override fun onProviderDisabled(provider: String) {
    }

    override fun onProviderEnabled(provider: String) {
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
    }

    /* private methods */

    private fun sendLocation() {
        if (location != null) {
            onLocationReceivedCallbacks.forEach { it.onReceived(location!!) }
        }
    }

    interface OnLocationReceived {
        fun onReceived(location: Location)
    }
}