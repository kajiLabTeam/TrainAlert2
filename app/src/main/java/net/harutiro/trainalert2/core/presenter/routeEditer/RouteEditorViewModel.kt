package net.harutiro.trainalert2.core.presenter.routeEditer

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.contentcapture.ContentCaptureManager.Companion.isEnabled
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.harutiro.trainalert2.core.presenter.home.HomeViewModel
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity
import net.harutiro.trainalert2.features.room.routeDB.repositories.RouteRepository

@Stable
class RouteEditorViewModel(private val homeViewModel: HomeViewModel) : ViewModel() {

    // Repositoryのインスタンスを取得
    private val repository = RouteRepository()

    var toastMessage: String? by mutableStateOf(null)
    var title by mutableStateOf("")
    var startLongitude by mutableStateOf("")
    var startLatitude by mutableStateOf("")
    var endLongitude by mutableStateOf("")
    var endLatitude by mutableStateOf("")
    var isNotificationEnabled by mutableStateOf(false)
    var isVibrationEnabled by mutableStateOf(false)
    var isEnabled by mutableStateOf(true)

    var currentRouteId: Int? = null // 編集するルートのIDを保持

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

    private fun validateInputs(): Boolean {
        return when {
            title.isBlank() -> {
                toastMessage = "ルート名を入力してください"
                false
            }
            startLongitude.isBlank() -> {
                toastMessage = "正しい出発地点の経度を入力してください"
                false
            }
            startLongitude.toDoubleOrNull() == null -> {
                toastMessage = "出発地点の経度には数値を入力してください"
                false
            }
            startLatitude.isBlank() -> {
                toastMessage = "正しい出発地点の緯度を入力してください"
                false
            }
            startLatitude.toDoubleOrNull() == null -> {
                toastMessage = "出発地点の緯度には数値を入力してください"
                false
            }
            endLongitude.isBlank() -> {
                toastMessage = "正しい到着地点の経度を入力してください"
                false
            }
            endLongitude.toDoubleOrNull() == null -> {
                toastMessage = "到着地点の経度には数値を入力してください"
                false
            }
            endLatitude.isBlank() -> {
                toastMessage = "正しい到着地点の緯度を入力してください"
                false
            }
            endLatitude.toDoubleOrNull() == null -> {
                toastMessage = "到着地点の緯度には数値を入力してください"
                false
            }
            else -> true
        }
    }

    // ルートデータをロードするメソッド
    fun loadRoute(routeId : Int) {
        viewModelScope.launch(Dispatchers.IO){
            val route = repository.getRouteById(routeId)

            title = route?.title ?: ""
            startLongitude = route?.startLongitude?.toString() ?: ""
            startLatitude = route?.startLatitude?.toString() ?:""
            endLongitude = route?.endLongitude?.toString() ?:""
            endLatitude = route?.endLatitude?.toString() ?:""
            isNotificationEnabled = route?.alertMethods == RouteEntity.NOTIFICATION || route?.alertMethods == RouteEntity.BOTH
            isVibrationEnabled = route?.alertMethods == RouteEntity.VIBRATION || route?.alertMethods == RouteEntity.BOTH
            isEnabled = route?.isEnabled ?: true
            currentRouteId = route?.id // ルートのIDを保存
        }
    }



    fun saveRoute(
        isNewRoute: Boolean,
        onSuccess: () -> Unit
    ) {
        if (!validateInputs()) return

        // アラート方法を決定
        val alertMethods = when {
            isNotificationEnabled && isVibrationEnabled -> RouteEntity.BOTH
            isNotificationEnabled -> RouteEntity.NOTIFICATION
            isVibrationEnabled -> RouteEntity.VIBRATION
            else -> RouteEntity.NOTIFICATION
        }

        // RouteEntityの作成
        val routeEntity = RouteEntity(
            id = currentRouteId ?: 0,
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

        // Repositoryを介してデータベースに保存
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isNewRoute) {
                    // 新規ルートを作成
                    repository.saveRoute(routeEntity)
                } else {
                    // 既存のルートを更新
                    repository.updateRoute(routeEntity)
                }

                // 保存処理が完了したらUIスレッドでコールバックを実行
                viewModelScope.launch(Dispatchers.Main) {
                    toastMessage = "ルートが保存されました"
                    onSuccess() // 成功時の処理を実行（遷移など）
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    toastMessage = "エラーが発生しました: ${e.message}"
                }
            }
        }
    }

    // ルートを削除する関数
    fun deleteRoute(route: RouteEntity, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteRoute(route) // Repository経由で削除メソッドを呼び出す
                // 削除が成功した場合にメインスレッドでコールバックを実行
                viewModelScope.launch(Dispatchers.Main) {
                    toastMessage = "ルートが削除されました"
                    onSuccess() // 成功時の処理を実行
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
