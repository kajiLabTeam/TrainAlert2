package net.harutiro.trainalert2.features.location.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.location.Location
import android.util.Log
import net.harutiro.trainalert2.features.location.api.LocationAPI
import net.harutiro.trainalert2.features.map.entity.LocationData

class LocationRepository {
    private var locationApi: LocationAPI? = null

    fun initLocationApi(context: Context) {
        locationApi = LocationAPI(context)
    }

    fun startLocationUpdates(
        getLocation: (LocationData) -> Unit,
    ) {
        locationApi?.startLocationUpdates(object : LocationAPI.MyLocationCallback {
            override fun onLocationResult(location: Location?) {
                if (location != null) {
                    getLocation(
                        LocationData(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )
                }
            }

            override fun onLocationError(error: String) {
                // エラー処理
                Log.d(TAG, "onLocationError")
            }
        })
    }
    fun stopLocationUpdates() {
        locationApi?.stopLocationUpdates()
    }
}