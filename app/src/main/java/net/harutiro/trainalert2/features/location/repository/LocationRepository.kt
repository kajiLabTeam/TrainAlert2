package net.harutiro.trainalert2.features.location.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.location.Location
import android.util.Log
import net.harutiro.trainalert2.features.location.api.LocationAPI
import net.harutiro.trainalert2.features.location.entity.CurrentLocationData

class LocationRepository {
    private var locationApi: LocationAPI? = null

    fun initLocationApi(context: Context) {
        locationApi = LocationAPI(context)
    }

    fun currentLocationUpdates(
        getLocation:(CurrentLocationData) -> Unit,
    ){
        //GoogleMap表示用（一度のみ位置情報取得）
        locationApi?.startLocationUpdates(object : LocationAPI.MyLocationCallback {
            override fun onLocationResult(location: Location?) {
                if (location != null) {
                    getLocation(
                        CurrentLocationData(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )
                }else{
                    Log.d(TAG, "onLocationResult: null")
                }
            }
            override fun onLocationError(error: String) {
                // エラー処理
                Log.d(TAG, "onLocationError")
            }
        })
    }
}