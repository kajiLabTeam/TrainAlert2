package net.harutiro.trainalert2.core.presenter.map


import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import net.harutiro.trainalert2.features.map.api.MapApi
import net.harutiro.trainalert2.features.map.repository.MapOptions
import net.harutiro.trainalert2.features.map.repository.MapRepository


internal class MapViewModel:ComponentActivity() {

    //val TAG = "MapViewModel"

    @Composable
    fun MapSetting(mapApi: MapApi, mapRepository: MapRepository, mapOptions: MapOptions) {

        var latitude by remember { mutableStateOf(0.0) }
        var longitude by remember { mutableStateOf(0.0) }

        val cameraPositionLocation = mapRepository.getMapLocationData(
            mapApi = mapApi,
            latitude = latitude,
            longitude = longitude
        )
        val cameraPositionState = mapOptions.MapCameraPosition(cameraPositionLocation)

        //GoogleMap呼び出し
        @Composable
        fun mapContent() {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
            ) {
                //パーミッションチェックのためcontext取得
                val context = LocalContext.current

                MapEffect(Unit) { map ->
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
        }
    }

}
