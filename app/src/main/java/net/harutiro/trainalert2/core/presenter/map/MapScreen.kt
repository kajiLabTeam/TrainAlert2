package net.harutiro.trainalert2.core.presenter.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import net.harutiro.trainalert2.core.presenter.component.ObserveLifecycleEvent
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity
import net.harutiro.trainalert2.features.map.repository.MapOptions

@Composable
fun MapScreen(
    viewModel: MapViewModel = viewModel()
    //,RouteEntity: RouteEntity
){
    viewModel.init(
        context = LocalContext.current,
    )

    //マップ表示
    MapSetting(
        viewModel = viewModel
        //,routeEntity = RouteEntity
    )

    ObserveLifecycleEvent { event ->
        // 検出したイベントに応じた処理を実装する。
        when (event) {
            Lifecycle.Event.ON_PAUSE -> {
                viewModel.stopLocationUpdates()
            }
            else -> {}
        }
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapSetting(
    viewModel: MapViewModel
    //,routeEntity: RouteEntity
) {
    val cameraPositionState = rememberCameraPositionState {
        val defaultPosition = LatLng(35.681236, 139.767125)
        val defaultZoom = 14f
        position = CameraPosition.fromLatLngZoom(defaultPosition, defaultZoom)
    }

    viewModel.changeLocation(
        cameraPositionState = cameraPositionState

    )

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        //パーミッションチェックのためcontext取得
        val context = LocalContext.current
        val mapOptions: MapOptions = MapOptions()

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

