package net.harutiro.trainalert2.core.presenter.map

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import net.harutiro.trainalert2.features.map.api.MapApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import net.harutiro.trainalert2.Application
import net.harutiro.trainalert2.core.presenter.component.ObserveLifecycleEvent
import net.harutiro.trainalert2.features.map.entity.LocationData
import net.harutiro.trainalert2.features.map.repository.MapOptions
import net.harutiro.trainalert2.features.map.repository.MapRepository

@Composable
fun MapScreen(
    viewModel: MapViewModel = viewModel()
){
    viewModel.init(
        context = LocalContext.current,
    )

    //マップ表示
    MapSetting(
        viewModel = viewModel
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

