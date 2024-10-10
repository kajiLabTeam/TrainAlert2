package net.harutiro.trainalert2.features.map.api

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority

class MapApi(private val context: Context) {
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

    fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
    }


    fun getLastLocation(callback: MyLocationCallback) {
        // 位置情報を取得するためのPermissionチェック
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 必要な場合、パーミッションを要求
            if (context is Activity) {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1000)
            }
            return
        }
        requestLocation(callback)
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation(callback: MyLocationCallback) {
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
    }

}