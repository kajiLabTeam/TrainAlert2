package net.harutiro.trainalert2.core.presenter.routeEditer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.contentcapture.ContentCaptureManager.Companion.isEnabled
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
    var isEnabled by mutableStateOf(true)

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
    // データを保存する関数
    @OptIn(ExperimentalComposeUiApi::class)
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
            alertMethods = alertMethods,
            isEnabled = isEnabled // ユーザの選択に基づいてルートの有効性を設定
        )

        // ルートが有効か無効かを判定
        if (isEnabled) {
            // ルートが有効に選択された場合の処理
            // ここに有効が選択された場合の処理を追加
        } else {
            // ルートが無効に選択された場合の処理
            // ここに無効が選択された場合の処理を追加
        }

        // Repositoryを介してデータベースに保存
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveRoute(routeEntity)
        }
    }


    fun deleteRoute() {
        TODO("Not yet implemented")
    }

}
