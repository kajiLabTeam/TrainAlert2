package net.harutiro.trainalert2.core.presenter.map


import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import net.harutiro.trainalert2.features.map.entity.LocationData
import net.harutiro.trainalert2.features.map.repository.MapRepository

class MapViewModel: ViewModel(){

    private val mapRepository = MapRepository()

    fun init(context: Context){
        mapRepository.initMapApi(context)
    }

    fun stopLocationUpdates(){
        mapRepository.stopLocationUpdates()
    }

    fun changeLocation(cameraPositionState: CameraPositionState) {
        mapRepository.getMapLocationData { locationData ->
            mapCameraPosition(
                cameraPosition = LocationData(
                    latitude = locationData.latitude,
                    longitude = locationData.longitude
                ),
                cameraPositionState = cameraPositionState
            )
        }
    }

    private fun mapCameraPosition(cameraPosition: LocationData, cameraPositionState: CameraPositionState) {
        //カメラ初期値
        val defaultPosition = LatLng(cameraPosition.latitude, cameraPosition.longitude)
        val defaultZoom = 14f
        cameraPositionState.position = CameraPosition.fromLatLngZoom(defaultPosition, defaultZoom)
    }
}