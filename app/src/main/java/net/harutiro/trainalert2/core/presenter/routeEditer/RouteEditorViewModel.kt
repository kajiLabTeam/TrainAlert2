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

    var alertMethods: Int by mutableStateOf(0b0000) // 初期値は0b0000


    fun onNotificationCheckedChange(checked: Boolean) {
        isNotificationEnabled = checked
        alertMethods = if (checked) {
            alertMethods or RouteEntity.NOTIFICATION
        } else {
            alertMethods and RouteEntity.NOTIFICATION.inv()
        }
    }

    fun onVibrationCheckedChange(checked: Boolean) {
        isVibrationEnabled = checked
        alertMethods = if (checked) {
            alertMethods or RouteEntity.VIBRATION
        } else {
            alertMethods and RouteEntity.VIBRATION.inv()
        }
    }

    fun onSoundCheckedChange(checked: Boolean) {
        isSoundEnabled = checked
        alertMethods = if (checked) {
            alertMethods or RouteEntity.SOUND
        } else {
            alertMethods and RouteEntity.SOUND.inv()
        }
    }

    fun onLightCheckedChange(checked: Boolean) {
        isLightEnabled = checked
        alertMethods = if (checked) {
            alertMethods or RouteEntity.LIGHT
        } else {
            alertMethods and RouteEntity.LIGHT.inv()
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
            currentRouteId = route?.id

            // アラート方法の状態を設定
            alertMethods = route?.alertMethods ?: 0b0000
            isNotificationEnabled = (alertMethods and RouteEntity.NOTIFICATION) != 0
            isVibrationEnabled = (alertMethods and RouteEntity.VIBRATION) != 0
            isSoundEnabled = (alertMethods and RouteEntity.SOUND) != 0
            isLightEnabled = (alertMethods and RouteEntity.LIGHT) != 0
        }
    }



    fun saveRoute(
        isNewRoute: Boolean,
        onSuccess: () -> Unit
    ) {
        if (!validateInputs()) return

        // 何も選択されていない場合、alertMethodsを0b0000として保存
        if (alertMethods == 0) {
            alertMethods = 0b0000
        }

        val routeEntity = RouteEntity(
            id = currentRouteId ?: 0,
            title = title,
            startLongitude = startLongitude.toDouble(),
            startLatitude = startLatitude.toDouble(),
            endLongitude = endLongitude.toDouble(),
            endLatitude = endLatitude.toDouble(),
            alertMethods = alertMethods,
            isEnabled = isEnabled
        )

        // 保存処理
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isNewRoute) {
                    repository.saveRoute(routeEntity)
                } else {
                    repository.updateRoute(routeEntity)
                }

                viewModelScope.launch(Dispatchers.Main) {
                    toastMessage = "ルートが保存されました"
                    onSuccess()
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
