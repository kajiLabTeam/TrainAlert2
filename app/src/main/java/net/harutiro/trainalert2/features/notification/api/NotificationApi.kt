package net.harutiro.trainalert2.features.notification.api

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.app.Activity

class NotificationApi(private val context: Context) {

    private val channelId = "train_alert_channel"
    private val notificationId = 1
    private val handler = Handler(Looper.getMainLooper())

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Train Alert Notifications",
                NotificationManager.IMPORTANCE_HIGH  // 高い重要度に設定
            )
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(title: String, message: String) {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // ヘッドアップ通知を表示するために高い優先度に設定
            .setAutoCancel(true)                           // 通知をタップしたら自動的に消す

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    fun vibrate(duration: Long = 500) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect = VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(duration)
        }
    }

    fun flashScreen(activity: Activity, flashCount: Int = 3, flashDuration: Long = 200) {
        var count = 0
        val originalColor = activity.window.decorView.background

        val flashRunnable = object : Runnable {
            override fun run() {
                if (count < flashCount) {
                    // 背景色を点滅させる
                    activity.window.decorView.setBackgroundColor(
                        if (count % 2 == 0) Color.WHITE else Color.BLACK
                    )
                    count++
                    handler.postDelayed(this, flashDuration)
                } else {
                    // 元の色に戻す
                    activity.window.decorView.background = originalColor
                }
            }
        }
        handler.post(flashRunnable)
    }
}