package com.jenshen.smartmirror.manager.location

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.annotation.RequiresPermission
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single

class FindLocationManager(private val context: Context) : IFindLocationManager {

    private val locationManager: LocationManager
    private val onLocationReceivedCallbacks: MutableList<OnLocationReceived>
    private val locationListeners: MutableList<LocationListener>

    private var loadLocationListener: LocationListener? = null

    private var location: Location? = null
    private var latitude: Double? = null
    private var longitude: Double? = null

    init {
        this.locationManager = context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
        this.onLocationReceivedCallbacks = mutableListOf()
        this.locationListeners = mutableListOf()
    }

    override fun addOnLocationReceivedCallback(onLocationReceived: OnLocationReceived) {
        this.onLocationReceivedCallbacks.add(onLocationReceived)
        sendLocation()
    }

    override fun removeOnLocationReceivedCallback(onLocationReceived: OnLocationReceived) {
        this.onLocationReceivedCallbacks.remove(onLocationReceived)
    }

    override fun canGetLocation() = IFindLocationManager.isLocationPermissionEnabled(context) && isProvidersEnabled()


    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun getCurrentLocation(minTimeToUpdate: Long,
                                    minDistanceToUpdate: Long): Single<Location> {
        return Single.create<Location>({ source ->
            val listener: LocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    if (!source.isDisposed) {
                        source.onSuccess(location)
                    }
                }

                override fun onProviderDisabled(provider: String) {
                }

                override fun onProviderEnabled(provider: String) {
                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                }
            }

            if (!canGetLocation()) {
                source.onError(RuntimeException("You mast check \"canGetLocation()\" methods"))
            } else {
                loadLocation(minTimeToUpdate, minDistanceToUpdate, listener)
            }
            source.setCancellable {
                stopLoadLocation(listener)
            }
        })
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun fetchCurrentLocation(minTimeToUpdate: Long,
                                      minDistanceToUpdate: Long): Flowable<Location> {
        return Flowable.create<Location>({ source ->
            val listener: LocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    if (!source.isCancelled) {
                        source.onNext(location)
                    }
                }

                override fun onProviderDisabled(provider: String) {
                }

                override fun onProviderEnabled(provider: String) {
                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                }
            }

            if (!canGetLocation()) {
                source.onError(RuntimeException("You mast check \"canGetLocation()\" methods"))
            } else {
                loadLocation(minTimeToUpdate, minDistanceToUpdate, listener)
            }
            source.setCancellable {
                stopLoadLocation(listener)
            }
        }, BackpressureStrategy.LATEST)
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun loadLocation(minTimeToUpdate: Long,
                              minDistanceToUpdate: Long) {
        loadLocationListener = object : LocationListener {
            override fun onLocationChanged(loc: Location) {
                location = loc
                sendLocation()
            }

            override fun onProviderDisabled(provider: String) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }
        }
        loadLocation(minTimeToUpdate, minDistanceToUpdate, loadLocationListener!!)
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun loadLocation(minTimeToUpdate: Long,
                              minDistanceToUpdate: Long,
                              locationListener: LocationListener) {

        // getting GPS status
        val isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)

        // getting network status
        val isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isNetworkEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTimeToUpdate,
                    minDistanceToUpdate.toFloat(), locationListener)

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
                        minTimeToUpdate,
                        minDistanceToUpdate.toFloat(), locationListener)

                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null) {
                    latitude = location!!.latitude
                    longitude = location!!.longitude
                }
            }
        }
        if (location != null) {
            locationListener.onLocationChanged(location)
        }
    }

    /**
     * Stop load current location
     * unregister loadLocationListener
     */
    override fun stopLoadLocation(locationListener: LocationListener) {
        locationListeners.remove(locationListener)
        locationManager.removeUpdates(locationListener)
    }

    /**
     * Stop load current location
     * unregister loadLocationListener
     */
    override fun stopLoadLocation() {
        if (loadLocationListener != null) {
            stopLoadLocation(loadLocationListener!!)
        }
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    override fun stopUsingGPS() {
        locationListeners.forEach { locationManager.removeUpdates(it) }
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

    /* private methods */

    private fun sendLocation() {
        if (location != null) {
            onLocationReceivedCallbacks.forEach { it.onReceived(location!!) }
        }
    }

    /**
     * Function to check GPS/wifi enabled

     * @return boolean
     */
    private fun isProvidersEnabled(): Boolean {
        // getting GPS status
        val isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)

        // getting network status
        val isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return isGPSEnabled || isNetworkEnabled
    }

    interface OnLocationReceived {
        fun onReceived(location: Location)
    }
}