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
    var isLightEnabled by mutableStateOf(false)
    var isSoundEnabled by mutableStateOf(false)
    var isEnabled by mutableStateOf(true)

    var currentRouteId: Int? = null // 編集するルートのIDを保持

    // アラート方法をビットフラグとして管理
    var alertMethods: Int by mutableStateOf(RouteEntity.getDefaultAlertMethod())

    fun onNotificationCheckedChange(checked: Boolean) {
        isNotificationEnabled = checked
        // 他のアラート方法が選択されていない場合、通知を選択する
        if (checked) {
            alertMethods = alertMethods or RouteEntity.NOTIFICATION
        } else {
            // 通知を解除する場合、他に選択されている方法があれば何もしない
            if (!isVibrationEnabled && !isLightEnabled && !isSoundEnabled) {
                alertMethods = alertMethods and RouteEntity.NOTIFICATION.inv()
            }
        }
    }

    fun onVibrationCheckedChange(checked: Boolean) {
        isVibrationEnabled = checked
        if (checked) {
            alertMethods = alertMethods or RouteEntity.VIBRATION
        } else {
            alertMethods = alertMethods and RouteEntity.VIBRATION.inv()
            // 他に選択肢がなければ「通知」を選択
            if (!isNotificationEnabled && !isLightEnabled && !isSoundEnabled) {
                alertMethods = alertMethods or RouteEntity.NOTIFICATION
            }
        }
    }

    fun onSoundCheckedChange(checked: Boolean) {
        isSoundEnabled = checked
        if (checked) {
            alertMethods = alertMethods or RouteEntity.SOUND
        } else {
            alertMethods = alertMethods and RouteEntity.SOUND.inv()
            if (!isNotificationEnabled && !isVibrationEnabled && !isLightEnabled) {
                alertMethods = alertMethods or RouteEntity.NOTIFICATION
            }
        }
    }

    fun onLightCheckedChange(checked: Boolean) {
        isLightEnabled = checked
        if (checked) {
            alertMethods = alertMethods or RouteEntity.LIGHT
        } else {
            alertMethods = alertMethods and RouteEntity.LIGHT.inv()
            if (!isNotificationEnabled && !isVibrationEnabled && !isSoundEnabled) {
                alertMethods = alertMethods or RouteEntity.NOTIFICATION
            }
        }
    }

    private fun validateInputs(): Boolean {
        return when {
            title.isBlank() -> {
                toastMessage = "ルート名を入力してください"
                false
            }
            startLongitude.isBlank() -> {
                toastMessage = "出発地点の経度を入力してください"
                false
            }
            startLongitude.toDoubleOrNull() == null -> {
                toastMessage = "出発地点の経度には数値を入力してください"
                false
            }
            startLatitude.isBlank() -> {
                toastMessage = "出発地点の緯度を入力してください"
                false
            }
            startLatitude.toDoubleOrNull() == null -> {
                toastMessage = "出発地点の緯度には数値を入力してください"
                false
            }
            endLongitude.isBlank() -> {
                toastMessage = "到着地点の経度を入力してください"
                false
            }
            endLongitude.toDoubleOrNull() == null -> {
                toastMessage = "到着地点の経度には数値を入力してください"
                false
            }
            endLatitude.isBlank() -> {
                toastMessage = "到着地点の緯度を入力してください"
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
    fun loadRoute(routeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val route = repository.getRouteById(routeId)

            title = route?.title ?: ""
            startLongitude = route?.startLongitude?.toString() ?: ""
            startLatitude = route?.startLatitude?.toString() ?: ""
            endLongitude = route?.endLongitude?.toString() ?: ""
            endLatitude = route?.endLatitude?.toString() ?: ""
            isEnabled = route?.isEnabled ?: true
            currentRouteId = route?.id // ルートのIDを保存

            // ルートが存在しない場合にのみデフォルトで通知を設定
            alertMethods = route?.alertMethods ?: RouteEntity.getDefaultAlertMethod()
            if (route == null) {
                alertMethods = RouteEntity.NOTIFICATION // デフォルトで通知を選択
            }
        }
    }




    fun saveRoute(
        isNewRoute: Boolean,
        onSuccess: () -> Unit
    ) {
        if (!validateInputs()) return

        // アラート方法が何も選択されていなかったら通知を選択
        if (alertMethods == RouteEntity.getDefaultAlertMethod()) {
            alertMethods = RouteEntity.NOTIFICATION
        }

        // RouteEntityの作成
        val routeEntity = RouteEntity(
            id = currentRouteId ?: 0,
            title = title,
            startLongitude = startLongitude.toDouble(),
            startLatitude = startLatitude.toDouble(),
            endLongitude = endLongitude.toDouble(),
            endLatitude = endLatitude.toDouble(),
            alertMethods = alertMethods, // ビットフラグを直接使用
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
