package net.harutiro.trainalert2.core.presenter.map


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.harutiro.trainalert2.features.map.entity.LocationData
import net.harutiro.trainalert2.features.map.repository.MapRepository
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

class MapViewModel : ViewModel() {

    private val mapRepository = MapRepository()

    fun init(context: Context) {
        mapRepository.initMapApi(context)

        loadAllRoutes()
        Log.d("viewModel", "$_routeList")
        Log.d("viewModel", "init")
    }

    fun stopLocationUpdates() {
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

    private fun mapCameraPosition(
        cameraPosition: LocationData,
        cameraPositionState: CameraPositionState
    ) {
        //カメラ初期値
        val defaultPosition = LatLng(cameraPosition.latitude, cameraPosition.longitude)
        val defaultZoom = 14f
        cameraPositionState.position = CameraPosition.fromLatLngZoom(defaultPosition, defaultZoom)
    }


    private val routeDao = MapRepository().routeDao()

    // StateFlowでルートリストを管理
    private val _routeList = MutableLiveData<List<RouteEntity>>(emptyList())

    val routeList: LiveData<List<RouteEntity>> = _routeList

    private fun loadAllRoutes() {
        viewModelScope.launch(Dispatchers.IO) {
            val routes = routeDao.loadAllRoute() // DB操作をバックグラウンドスレッドで実行
            _routeList.postValue(routes.toList()) // postValueでUIスレッドに反映
            Log.d("routeList", "$routes")
        }
    }
}