package net.harutiro.trainalert2.features.map.repository

import android.content.ContentValues.TAG
import android.location.Location
import android.util.Log
import net.harutiro.trainalert2.features.map.api.MapApi
import net.harutiro.trainalert2.features.map.entity.LocationData

//マップの中心の値の取得
class MapRepository {

    private var mapApi: MapApi? = null

    fun initMapApi(context: android.content.Context) {
        mapApi = MapApi(context)
    }

    fun stopLocationUpdates() {
        mapApi?.stopLocationUpdates()
    }

    fun getMapLocationData(
        getLocation:(LocationData) -> Unit
    ){
        //GoogleMap表示用（一度のみ位置情報取得）
        mapApi?.getLastLocation(object : MapApi.MyLocationCallback {
            override fun onLocationResult(location: Location?) {
                if (location != null) {
                    getLocation(
                        LocationData(
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
