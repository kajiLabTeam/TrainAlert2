package net.harutiro.trainalert2.features.map.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import net.harutiro.trainalert2.TrainAlertApplication
import net.harutiro.trainalert2.R
import net.harutiro.trainalert2.features.map.api.MapApi
import net.harutiro.trainalert2.features.map.entity.LocationData
import net.harutiro.trainalert2.features.room.routeDB.apis.RouteDao

//マップの中心の値の取得
class MapRepository {

    private var mapApi: MapApi? = null
    private var context: Context? = null

    fun initMapApi(context: Context) {
        mapApi = MapApi(context)
    }

    fun stopLocationUpdates() {
        mapApi?.stopLocationUpdates()
    }

    fun getMapLocationData(
        getLocation:(LocationData) -> Unit,
    ){
        //GoogleMap表示用（一度のみ位置情報取得）
        mapApi?.getLastLocation(object : MapApi.MyLocationCallback {
            override fun onLocationResult(location: Location?) {
                if (location != null) {
                    getLocation(
                        LocationData(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )
                }else{
                    Log.d(TAG, "onLocationResult: null")
                    Toast.makeText(context, context?.getString(R.string.location_is_null), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onLocationError(error: String) {
                // エラー処理
                Log.d(TAG, "onLocationError")
                Toast.makeText(context, context?.getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun routeDao(): RouteDao {
        return TrainAlertApplication.database.routeDao()
    }
}
