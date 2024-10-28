package net.harutiro.trainalert2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.harutiro.trainalert2.core.presenter.FirstPage
import net.harutiro.trainalert2.features.location.repository.DistanceJudgement
import net.harutiro.trainalert2.features.notification.api.NotificationApi
import net.harutiro.trainalert2.features.room.routeDB.repositories.RouteRepository
import net.harutiro.trainalert2.ui.theme.TrainAlert2Theme

class MainActivity : ComponentActivity() {

    private val routeRepository = RouteRepository()
    private var notificationApi: NotificationApi? = null

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

        // 距離判定の処理
        checkDistance()
    }

    private fun checkDistance() {
        lifecycleScope.launch(Dispatchers.IO) {
            // データベースから目的地の情報を取得
            val routeList = routeRepository.loadAllRoutes() // データベースにあるルート全てを取得する
            if (routeList.isNotEmpty()) {
                // 全ルートに対して処理を行う
                routeList.forEach { destination ->
                    // 現在地を取得する部分はAyakaさんが実装予定
                    val currentLocation = LatLng(35.681236, 139.767125) // 仮の現在地（東京駅）

                    // 目的地を取得
                    val destinationLocation = LatLng(
                        destination.endLatitude ?: 0.0,
                        destination.endLongitude ?: 0.0
                    )

                    // 距離判定を行う
                    val isWithinDistance = DistanceJudgement().withinJudgerange(
                        currentLocation.latitude, currentLocation.longitude,
                        destinationLocation.latitude, destinationLocation.longitude,
                        100.0 // 距離は100m以内か
                    )

                    // 判定がtrueの場合、通知を表示
                    if (isWithinDistance) {
                        if(destination.alertMethods == 1) {
                            withContext(Dispatchers.Main) {
                                notificationApi?.showNotification(
                                    "目的地に近づきました",
                                    "間もなく到着です！"
                                )
                            }
                        }else if(destination.alertMethods == 2){
                            withContext(
                                Dispatchers.Main
                            ) {
                                notificationApi?.vibrate()
                                }
                        }
                    }
                }
            }
        }
    }
}