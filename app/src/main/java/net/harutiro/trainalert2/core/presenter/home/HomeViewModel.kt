package net.harutiro.trainalert2.core.presenter.home

import androidx.lifecycle.ViewModel
import android.content.Context
import net.harutiro.trainalert2.features.notification.api.NotificationApi

class HomeViewModel: ViewModel() {

    fun test(context: Context) {
        // NotificationApiインスタンスを作成
        val notificationApi = NotificationApi(context)

        // 通知を表示
        notificationApi.showNotification("テスト通知", "これはテストメッセージです。")

        // バイブレーションを実行
        notificationApi.vibrate(500) // 500msのバイブレーション
    }
}
