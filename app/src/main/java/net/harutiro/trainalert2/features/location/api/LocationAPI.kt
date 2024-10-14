package net.harutiro.trainalert2.features.location.api

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority

class LocationAPI(private val context: Context) {
    private var getRate: Long = 60000//取得頻度(ms)1分
    private var minRate: Long = 60000//更新頻度(ms)1分

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null

    interface MyLocationCallback {
        fun onLocationResult(location: Location?)
        fun onLocationError(error: String)
    }

    init {
        // LocationRequestの設定
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, getRate) // 1分ごとに取得
            .setMinUpdateIntervalMillis(minRate) // 最小間隔1分
            .build()
    }

    fun startLocationUpdates(callback: MyLocationCallback) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (context is Activity) {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1000)
            }
            return
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    callback.onLocationResult(location)
                }
            }

            override fun onLocationAvailability(availability: LocationAvailability) {
                if (!availability.isLocationAvailable) {
                    callback.onLocationError("Location unavailable")
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest!!, locationCallback!!, null)
    }

    fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation(callback: MyLocationCallback) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        callback.onLocationResult(location)
                    } else {
                        callback.onLocationError("Location is null")
                    }
                }
                .addOnFailureListener { exception ->
                    callback.onLocationError(exception.message ?: "Unknown error")
                }
        }else{
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1000)
        }
    }
}

