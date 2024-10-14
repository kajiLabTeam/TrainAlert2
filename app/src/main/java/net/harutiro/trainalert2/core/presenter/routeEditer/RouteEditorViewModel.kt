package net.harutiro.trainalert2.core.presenter.routeEditer

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.harutiro.trainalert2.features.room.routeDB.apis.RouteDao
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

class RouteEditorViewModel(private val routeDao: RouteDao): ViewModel() {


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

    fun saveRoute(context: Context) {
        val alertMethods = mutableListOf<String>()
        if (isNotificationEnabled) alertMethods.add("通知")
        if (isVibrationEnabled) alertMethods.add("バイブレーション")

        // バリデーションチェック
        if (alertMethods.isEmpty()) {
            Toast.makeText(context, "通知またはバイブレーションを選択してください。", Toast.LENGTH_SHORT).show()
            return
        }

        val newRoute = RouteEntity(
            title = title,
            startLongitude = startLongitude.toDoubleOrNull(),
            startLatitude = startLatitude.toDoubleOrNull(),
            endLongitude = endLongitude.toDoubleOrNull(),
            endLatitude = endLatitude.toDoubleOrNull(),
            alertMethods = alertMethods.joinToString(", ")
        )

        viewModelScope.launch(Dispatchers.IO) {
            routeDao.saveRoute(newRoute)
        }
    }

}