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

class RouteEditorViewModel : ViewModel() {
    private val repository: RouteRepository = RouteRepository()

    var toastMessage: String? by mutableStateOf(null)
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
            isNotificationEnabled = true
        }
    }

    fun onVibrationCheckedChange(checked: Boolean) {
        isVibrationEnabled = checked
        if (!isNotificationEnabled && !isVibrationEnabled) {
            isNotificationEnabled = true
        }
    }

    private fun validateInputs(): Boolean {
        return when {
            title.isBlank() -> {
                toastMessage = "ルート名を入力してください"
                false
            }
            startLongitude.toDoubleOrNull() == null -> {
                toastMessage = "正しい出発地点の経度を入力してください"
                false
            }
            startLatitude.toDoubleOrNull() == null -> {
                toastMessage = "正しい出発地点の緯度を入力してください"
                false
            }
            endLongitude.toDoubleOrNull() == null -> {
                toastMessage = "正しい到着地点の経度を入力してください"
                false
            }
            endLatitude.toDoubleOrNull() == null -> {
                toastMessage = "正しい到着地点の緯度を入力してください"
                false
            }
            else -> true
        }
    }

    fun saveRoute(onSuccess: () -> Unit) {
        if (!validateInputs()) return

        // アラート方法を定義
        val alertMethods = when {
            isNotificationEnabled && isVibrationEnabled -> RouteEntity.BOTH
            isNotificationEnabled -> RouteEntity.NOTIFICATION
            isVibrationEnabled -> RouteEntity.VIBRATION
            else -> RouteEntity.NOTIFICATION // デフォルトで通知
        }

        // RouteEntity インスタンスを作成
        val routeEntity = RouteEntity(
            title = title,
            startLongitude = startLongitude.toDouble(),
            startLatitude = startLatitude.toDouble(),
            endLongitude = endLongitude.toDouble(),
            endLatitude = endLatitude.toDouble(),
            alertMethods = when {
                isNotificationEnabled && isVibrationEnabled -> RouteEntity.BOTH
                isNotificationEnabled -> RouteEntity.NOTIFICATION
                isVibrationEnabled -> RouteEntity.VIBRATION
                else -> RouteEntity.NOTIFICATION // デフォルトで通知
            },
            isEnabled = isEnabled
        )


        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.saveRoute(routeEntity)
                // 保存が成功した場合にメインスレッドでコールバックを実行
                viewModelScope.launch(Dispatchers.Main) {
                    toastMessage = "ルートが保存されました"
                    onSuccess()
                }
            } catch (e: Exception) {
                // エラーメッセージをメインスレッドで設定
                viewModelScope.launch(Dispatchers.Main) {
                    toastMessage = "エラーが発生しました: ${e.message}"
                }
            }
        }
    }
}
