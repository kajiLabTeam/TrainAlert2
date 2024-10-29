package net.harutiro.trainalert2.core.presenter.LocationSelect

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import net.harutiro.trainalert2.features.map.repository.MapOptions
import java.util.Locale

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun LocationSelectScreen(
    toBackEditor:(LatLng) -> Unit,
    viewModel:LocationSelectViewModel = viewModel(),
){
    val context = LocalContext.current
    viewModel.init(context)

    val cameraPositionState = rememberCameraPositionState {
        val defaultPosition = LatLng(35.681236, 139.767125)
        val defaultZoom = 14f
        position = CameraPosition.fromLatLngZoom(defaultPosition, defaultZoom)
    }

    // 選択された位置を保持するステート
    var selectedLocation by remember { mutableStateOf(LatLng(0.0,0.0)) }
    val markerState = remember { MarkerState(selectedLocation) }
    LaunchedEffect(selectedLocation) {
        markerState.position = selectedLocation
    }

    viewModel.changeLocation(
        cameraPositionState = cameraPositionState
    )


    Box{
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                selectedLocation = latLng
            }
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

            Marker(
                state = markerState,
                title =
                    "緯度: ${
                        String.format(
                            Locale.JAPAN,
                            "%.6f",
                            selectedLocation.latitude
                        )
                    }　経度: ${
                        String.format(
                            Locale.JAPAN,
                            "%.6f",
                            selectedLocation.longitude
                        )
                    }"
            )
        }

        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 62.dp)
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = "緯度: ${String.format(Locale.JAPAN, "%.6f", selectedLocation.latitude)}")
                Text(text = "経度: ${String.format(Locale.JAPAN, "%.6f", selectedLocation.longitude)}")
            }
        }

        Button(
            onClick = {
                selectedLocation = LatLng(0.0,0.0)
                toBackEditor(selectedLocation)
            },
            enabled = selectedLocation != LatLng(0.0,0.0),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ){
            Text("この座標で保存する")
        }

    }


}