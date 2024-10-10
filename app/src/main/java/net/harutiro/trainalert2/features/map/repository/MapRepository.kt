package net.harutiro.trainalert2.features.map.repository

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import net.harutiro.trainalert2.features.map.api.MapApi
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap

class MapRepository {
    @Composable
    fun MapContent(mapApi: MapApi?){
        var latitude by remember { mutableStateOf(0.0) }
        var longitude by remember { mutableStateOf(0.0) }

        val cameraPositionState = rememberCameraPositionState()
        //カメラ初期値
        fun updateCameraPosition(latitude: Double, longitude: Double) {
            val defaultPosition = LatLng(latitude, longitude)
            val defaultZoom = 14f
            cameraPositionState.position = CameraPosition.fromLatLngZoom(defaultPosition, defaultZoom)
        }

        //GoogleMap表示用（一度のみ位置情報取得）
        mapApi?.getLastLocation(object : MapApi.MyLocationCallback {
            override fun onLocationResult(location: Location?) {
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                    //カメラ位置、範囲の設定
                    updateCameraPosition(latitude, longitude)
                }
            }

            override fun onLocationError(error: String) {
                // エラー処理
            }
        })
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ){
        }
    }
}