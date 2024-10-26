package net.harutiro.trainalert2.core.presenter.routeEditer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity
import net.harutiro.trainalert2.features.room.routeDB.repositories.RouteRepository

class RouteEditorViewModel: ViewModel() {
    // Repositoryのインスタンスを取得
    private val repository = RouteRepository()

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
            isNotificationEnabled && isVibrationEnabled -> RouteEntity.BOTH
            isNotificationEnabled -> RouteEntity.NOTIFICATION
            isVibrationEnabled -> RouteEntity.VIBRATION
            else -> RouteEntity.NOTIFICATION
        }

        // RouteEntityの作成
        val routeEntity = RouteEntity(
            title = title,
            startLongitude = startLongitude.toDoubleOrNull(),
            startLatitude = startLatitude.toDoubleOrNull(),
            endLongitude = endLongitude.toDoubleOrNull(),
            endLatitude = endLatitude.toDoubleOrNull(),
            alertMethods = alertMethods // 数値型で保存
        )

        // Repositoryを介してデータベースに保存
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveRoute(routeEntity)
        }
    }


    // ルートを削除する関数
    fun deleteRoute(route: RouteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRoute(route) // Repository経由で削除メソッドを呼び出す
        }
    }

}