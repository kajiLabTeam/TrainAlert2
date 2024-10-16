package net.harutiro.trainalert2.features.notification.repository

import android.location.Location
import net.harutiro.trainalert2.features.notification.api.NotificationApi

class NotificationRepository(
    private val notificationApi: NotificationApi
) {

    // 目的地の緯度・経度を指定
    private val destinationLatitude = 35.681236 // 例: 東京駅の緯度
    private val destinationLongitude = 139.767125 // 例: 東京駅の経度
    private val notificationRadius = 500.0 // メートル

    /**
     * 現在のユーザーの位置情報を受け取り、通知を表示するかを判断します。
     */
    fun checkProximityAndNotify(userLatitude: Double, userLongitude: Double) {
        val userLocation = Location("").apply {
            latitude = userLatitude
            longitude = userLongitude
        }

        val destinationLocation = Location("").apply {
            latitude = destinationLatitude
            longitude = destinationLongitude
        }

        // ユーザーと目的地の距離を計算
        val distanceToDestination = userLocation.distanceTo(destinationLocation)

        // 半径500メートル以内に入ったら通知を表示
        if (distanceToDestination <= notificationRadius) {
            notificationApi.showNotification(
                title = "近くに到着しました！",
                message = "目的地に500メートル以内に入りました。"
            )
        }
    }
}
