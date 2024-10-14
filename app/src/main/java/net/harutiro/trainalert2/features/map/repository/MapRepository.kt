package net.harutiro.trainalert2.features.map.repository

import android.location.Location
import androidx.compose.runtime.Composable
import net.harutiro.trainalert2.features.map.api.MapApi
import net.harutiro.trainalert2.features.map.entity.LocationData

//マップの中心の値の取得
class MapRepository {
    @Composable
    fun getMapLocationData(mapApi: MapApi?, latitude:Double, longitude:Double):LocationData{

        var lastlatitude = latitude
        var lastlongitude = longitude

        //GoogleMap表示用（一度のみ位置情報取得）
        mapApi?.getLastLocation(object : MapApi.MyLocationCallback {

            override fun onLocationResult(location: Location?) {
                if (location != null) {
                    lastlatitude = location.latitude
                    lastlongitude = location.longitude
                }
            }

            override fun onLocationError(error: String) {
                // エラー処理
            }
        })
        return LocationData(lastlatitude,lastlongitude)
    }
}
