package net.harutiro.trainalert2.features.map.api

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class MapApi(private val context: Context) {
    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    interface MyLocationCallback {
        fun onLocationResult(location: Location?)
        fun onLocationError(error: String)
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

    private fun requestLocation(callback: MyLocationCallback) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        callback.onLocationResult(location)
                    } else {
                        callback.onLocationError("Location is null")
                        Toast.makeText(context, "Location is null", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    callback.onLocationError(exception.message ?: "Unknown error")
                    Toast.makeText(context, "Unknown error", Toast.LENGTH_SHORT).show()
                }
        }else{
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1000)
        }
    }

}