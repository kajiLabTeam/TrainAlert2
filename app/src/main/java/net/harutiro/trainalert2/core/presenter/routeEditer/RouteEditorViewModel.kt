package net.harutiro.trainalert2.core.presenter.routeEditer

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RouteEditorViewModel: ViewModel() {

    val TAG = "RouteEditorViewModel"

    fun test(){
        Log.d(TAG,"経路編集画面のViewModel")
    }

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
            isNotificationEnabled = true// 通知、バイブレーションのどちらも選択されていない場合は、自動的に通知を選択
        }
    }
}