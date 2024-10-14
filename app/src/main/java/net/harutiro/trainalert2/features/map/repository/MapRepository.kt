package net.harutiro.trainalert2.features.map.repository

import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import net.harutiro.trainalert2.features.map.api.MapApi

//マップの中心の値の取得
class MapRepository {
    @Composable
    fun MapContent(mapApi: MapApi?, mapEffects: MapEffects){

        var lastlatitude by remember { mutableStateOf(0.0) }
        var lastlongitude by remember { mutableStateOf(0.0) }

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
        //mapEffectsを呼び出して位置情報をカメラに入れる
        //mapEffects.MapShape(maplatitude = lastlatitude, maplongitude = lastlongitude)
    }
}
