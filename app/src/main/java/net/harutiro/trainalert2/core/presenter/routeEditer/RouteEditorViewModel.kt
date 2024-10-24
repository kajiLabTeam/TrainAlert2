import android.widget.Toast
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

    companion object {
        const val NOTIFICATION = 1
        const val VIBRATION = 2
        const val BOTH = 3
    }

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
        if (!validateInputs()) {
            // 無効な入力があった場合は何もせずリターン
            return
        }

        val routeEntity = RouteEntity(
            title = title,
            startLongitude = startLongitude.toDouble(),
            startLatitude = startLatitude.toDouble(),
            endLongitude = endLongitude.toDouble(),
            endLatitude = endLatitude.toDouble(),
            alertMethods = when {
                isNotificationEnabled && isVibrationEnabled -> BOTH
                isNotificationEnabled -> NOTIFICATION
                isVibrationEnabled -> VIBRATION
                else -> NOTIFICATION
            }
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
