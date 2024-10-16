package net.harutiro.trainalert2.core.presenter.home

import androidx.lifecycle.ViewModel
import android.content.Context
import net.harutiro.trainalert2.features.notification.api.NotificationApi

class HomeViewModel(private val context: Context): ViewModel() {

    private val notificationApi = NotificationApi(context)

    fun test() {
        // テスト通知を表示
        notificationApi.showNotification("テスト通知", "これはテストメッセージです。")
    }
}
