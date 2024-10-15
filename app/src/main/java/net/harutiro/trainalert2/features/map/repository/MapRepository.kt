package net.harutiro.trainalert2.features.map.repository

import android.location.Location
import net.harutiro.trainalert2.features.map.api.MapApi
import net.harutiro.trainalert2.features.map.entity.LocationData

//マップの中心の値の取得
class MapRepository {
    fun getMapLocationData(
        mapApi: MapApi?,
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
                }
            }
            override fun onLocationError(error: String) {
                // エラー処理
            }
        })
    }
}
