package net.harutiro.trainalert2.features.location.repository

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
//import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Build
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.harutiro.trainalert2.features.location.entity.CurrentLocationData
//import net.harutiro.trainalert2.features.location.repository.DistanceJudgement
//import net.harutiro.trainalert2.features.location.repository.LocationRepository
import net.harutiro.trainalert2.features.notification.api.NotificationApi
import net.harutiro.trainalert2.features.room.routeDB.repositories.RouteRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.DelicateCoroutinesApi
import net.harutiro.trainalert2.MainActivity
import net.harutiro.trainalert2.R

class LocationService : Service() {

    private val routeRepository = RouteRepository()
    private val notificationApi = NotificationApi(this)
    private val locationRepository = LocationRepository()

    override fun onCreate() {
        super.onCreate()
        locationRepository.initLocationApi(this)
        startForegroundService()
        checkDistance()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startForegroundService() {
        val notificationChannelId = "LocationServiceChannel"
        val channelName = "Location Service"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("TrainAlert2")
            .setContentText("位置情報を監視中")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE))
            .build()

        startForeground(1, notification)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun checkDistance() {
        val judgerange = 600.0 // 判定距離

        locationRepository.currentLocationUpdates { currentLocationData ->

            GlobalScope.launch(Dispatchers.IO) {
                val routeList = routeRepository.loadAllRoutes()
                if (routeList.isEmpty()) return@launch

                val currentLocation = CurrentLocationData(
                    currentLocationData.latitude,
                    currentLocationData.longitude
                )

                routeList.forEach { destination ->
                    val destinationLocation = LatLng(
                        destination.endLatitude ?: 0.0,
                        destination.endLongitude ?: 0.0
                    )

                    val isWithinDistance = DistanceJudgement().resultDistance(
                        currentLocation.latitude, currentLocation.longitude,
                        destinationLocation.latitude, destinationLocation.longitude,
                        judgerange
                    )

                    if (isWithinDistance) {
                        launch(Dispatchers.Main) {
                            when (destination.alertMethods) {
                                1 -> notificationApi.showNotification(
                                    "目的地に近づきました",
                                    "間もなく到着です！"
                                )
                                2 -> notificationApi.vibrate()
                            }
                        }
                    }
                }
            }
        }
    }
}