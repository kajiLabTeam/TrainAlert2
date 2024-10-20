package net.harutiro.trainalert2.core.presenter.routeEditer

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.harutiro.trainalert2.MyApplication
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity
import net.harutiro.trainalert2.features.room.routeDB.repositories.RouteRepository


class RouteEditorViewModel: ViewModel() {

    // Repositoryのインスタンスを取得
    private val repository = RouteRepository(MyApplication.database.routeDao())

    var title by mutableStateOf("")
    var startLongitude by mutableStateOf("")
    var startLatitude by mutableStateOf("")
    var endLongitude by mutableStateOf("")
    var endLatitude by mutableStateOf("")
    var isNotificationEnabled by mutableStateOf(false)
    var isVibrationEnabled by mutableStateOf(false)

    fun onNotificationCheckedChange(checked: Boolean) {
        isNotificationEnabled = checked
        if (!isNotificationEnabled && !isVibrationEnabled) {
            isNotificationEnabled = true // 通知、バイブレーションのどちらも選択されていない場合は、自動的に通知を選択
        }
    }

    fun onVibrationCheckedChange(checked: Boolean) {
        isVibrationEnabled = checked
        if (!isNotificationEnabled && !isVibrationEnabled) {
            isNotificationEnabled = true // 通知、バイブレーションのどちらも選択されていない場合は、自動的に通知を選択
        }
    }

    // データを保存する関数
    fun saveRoute() {
        // アラート方法を決定
        val alertMethods = when {
            isNotificationEnabled && isVibrationEnabled -> "Notification, Vibration"
            isNotificationEnabled -> "Notification"
            isVibrationEnabled -> "Vibration"
            else -> "Notification" // 両方選択されていない場合はデフォルトで通知
        }

        // RouteEntityの作成
        val routeEntity = RouteEntity(
            title = title,
            startLongitude = startLongitude.toDoubleOrNull(),
            startLatitude = startLatitude.toDoubleOrNull(),
            endLongitude = endLongitude.toDoubleOrNull(),
            endLatitude = endLatitude.toDoubleOrNull(),
            alertMethods = alertMethods
        )

        // Repositoryを介してデータベースに保存
        viewModelScope.launch {
            repository.saveRoute(routeEntity)
        }
    }

}
