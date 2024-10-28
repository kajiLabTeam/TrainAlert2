package net.harutiro.trainalert2.core.presenter.LocationSelect

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import net.harutiro.trainalert2.features.map.repository.MapOptions

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun LocationSelectScreen(
    viewModel:LocationSelectViewModel = viewModel()
){

    val context = LocalContext.current
    viewModel.init(context)

    val cameraPositionState = rememberCameraPositionState {
        val defaultPosition = LatLng(35.681236, 139.767125)
        val defaultZoom = 14f
        position = CameraPosition.fromLatLngZoom(defaultPosition, defaultZoom)
    }

    viewModel.changeLocation(
        cameraPositionState = cameraPositionState
    )


    Box{
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            MapEffect { map ->
                //パーミッションチェック
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    map.isMyLocationEnabled = true
                }
            }
        }

        Button(
            onClick = {
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ){
            Text("この座標で保存する")
        }

    }


}