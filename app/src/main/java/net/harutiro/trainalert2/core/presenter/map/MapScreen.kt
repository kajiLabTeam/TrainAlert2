package net.harutiro.trainalert2.core.presenter.map

import android.Manifest
import android.content.pm.PackageManager
import net.harutiro.trainalert2.features.map.api.MapApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import net.harutiro.trainalert2.core.presenter.component.ObserveLifecycleEvent
import net.harutiro.trainalert2.features.map.entity.LocationData
import net.harutiro.trainalert2.features.map.repository.MapOptions
import net.harutiro.trainalert2.features.map.repository.MapRepository

@Composable
fun MapScreen(){
    val context = LocalContext.current
    val mapApi = MapApi(context = context)
    //マップ表示
    MapSetting(mapApi = mapApi, mapRepository = MapRepository(), mapOptions = MapOptions())

    ObserveLifecycleEvent { event ->
        // 検出したイベントに応じた処理を実装する。
        when (event) {
            Lifecycle.Event.ON_PAUSE -> {
                mapApi.stopLocationUpdates()
            }
            else -> {}
        }
    }

}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapSetting(mapApi: MapApi, mapRepository: MapRepository, mapOptions: MapOptions) {

    val latitude by remember { mutableStateOf(0.0) }
    val longitude by remember { mutableStateOf(0.0) }

    val cameraPositionLocation = mapRepository.getMapLocationData(
        mapApi = mapApi,
        latitude = latitude,
        longitude = longitude
    )
    val cameraPositionState = mapCameraPosition(cameraPositionLocation)

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        //パーミッションチェックのためcontext取得
        val context = LocalContext.current

        MapEffect(Unit) { map ->
            //パーミッションチェック
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {
                map.isMyLocationEnabled = true
            }
        }
    }
}

@Composable
fun mapCameraPosition(cameraPosition: LocationData): CameraPositionState {
    //カメラ初期値
    val defaultPosition = LatLng(cameraPosition.latitude, cameraPosition.longitude)
    val defaultZoom = 14f
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPosition, defaultZoom)
    }
    return cameraPositionState
}
