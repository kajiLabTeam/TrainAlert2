package net.harutiro.trainalert2.features.notification.api

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import net.harutiro.trainalert2.R
import kotlin.random.Random

class NotificationApi(private val context: Context) {

    private val channelId = "train_alert_channel"
    private val notificationId = 1

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

    fun vibrate() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // ランダムなパターンを生成
            val timings = LongArray(10) { Random.nextLong(100, 500) }
            val amplitudes = IntArray(10) { Random.nextInt(50, 255) }

            val vibrationEffect = VibrationEffect.createWaveform(timings, amplitudes, -1) // 一回のみの振動
            vibrator.vibrate(vibrationEffect)
        } else {
            // 旧バージョンはランダムなパターンで振動を繰り返し
            val pattern = LongArray(20) { Random.nextLong(100, 500) }
            vibrator.vibrate(pattern, -1)
        }
    }

    fun playSounds() {
        val playCount = 10
        var currentPlay = 0
        startPlayingSound(playCount, currentPlay)
    }

    fun startPlayingSound(playCount: Int, currentPlay: Int) {
        if (currentPlay >= playCount) return

        MediaPlayer.create(context, R.raw.notification_sound).apply {
            setOnCompletionListener {
                release()
                startPlayingSound(playCount, currentPlay + 1)
            }
            start()
        }
    }
}
