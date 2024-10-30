package net.harutiro.trainalert2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.harutiro.trainalert2.core.presenter.FirstPage
import net.harutiro.trainalert2.features.location.entity.CurrentLocationData
import net.harutiro.trainalert2.features.location.repository.DistanceJudgement
import net.harutiro.trainalert2.features.location.repository.LocationRepository
import net.harutiro.trainalert2.features.notification.api.NotificationApi
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity
import net.harutiro.trainalert2.features.room.routeDB.repositories.RouteRepository
import net.harutiro.trainalert2.ui.theme.TrainAlert2Theme

class MainActivity : ComponentActivity() {

    private val routeRepository = RouteRepository()
    private var notificationApi: NotificationApi? = null
    private val locationRepository = LocationRepository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrainAlert2Theme {
                FirstPage()
            }
        }

        // NotificationApiのインスタンスを作成
        notificationApi = NotificationApi(this)

        locationRepository.initLocationApi(this)

        // 距離判定の処理
        checkDistance()
    }

    private fun checkDistance() {
        val judgerange = 600.0 // 判定距離

        locationRepository.currentLocationUpdates { CurrentLocationData ->

            Log.d("currentLocation", "$CurrentLocationData")

            lifecycleScope.launch(Dispatchers.IO) {
                // データベースから目的地の情報を取得
                val routeList = routeRepository.loadAllRoutes() // データベースにあるルート全てを取得する
                if (routeList.isEmpty()) {
                    return@launch
                }

                val currentLocation = CurrentLocationData (
                    CurrentLocationData.latitude,
                    CurrentLocationData.longitude
                )
                Log.d("currentLocation", "$currentLocation")

                // 全ルートに対して処理を行う
                routeList.forEach { destination ->
                    // 目的地を取得
                    val destinationLocation = LatLng(
                        destination.endLatitude ?: 0.0,
                        destination.endLongitude ?: 0.0
                    )

                    // 距離判定を行う
                    val isWithinDistance = DistanceJudgement().resultDistance(
                        currentLocation.latitude, currentLocation.longitude,
                        destinationLocation.latitude, destinationLocation.longitude,
                        judgerange // 距離は100m以内か
                    )

                    // 判定がtrueの場合、通知を表示
                    if (isWithinDistance) {
                        if(destination.alertMethods and RouteEntity.NOTIFICATION == RouteEntity.NOTIFICATION || destination.alertMethods  == RouteEntity.NONE) {
                            withContext(Dispatchers.Main) {
                                notificationApi?.showNotification(
                                    "目的地に近づきました",
                                    "間もなく到着です！"
                                )
                            }
                        }else if(destination.alertMethods and RouteEntity.VIBRATION == RouteEntity.VIBRATION) {
                            withContext(
                                Dispatchers.Main
                            ) {
                                notificationApi?.vibrate()
                            }
                        }else if(destination.alertMethods and RouteEntity.LIGHT == RouteEntity.LIGHT) {
                            //画面点滅処理呼び出し
                            Log.d("light", "light")
                        }else if(destination.alertMethods and RouteEntity.SOUND == RouteEntity.SOUND) {
                            //サウンド再生処理呼び出し
                            Log.d("sound", "sound")
                        }
                    }
                }
            }

        }


    }
}