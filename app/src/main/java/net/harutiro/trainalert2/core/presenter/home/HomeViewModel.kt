package net.harutiro.trainalert2.core.presenter.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.harutiro.trainalert2.TrainAlertApplication
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

class HomeViewModel: ViewModel() {

    val TAG = "HomeViewModel"

    fun test(){
        Log.d(TAG,"ホーム画面のViewModel")
    }

    private val routeDao = TrainAlertApplication.database.routeDao()
    // StateFlowでルートリストを管理
    private val _routeList = MutableStateFlow<List<RouteEntity>>(emptyList())
    val routeList: StateFlow<List<RouteEntity>> = _routeList

    init {
        // データをロードする
        loadAllRoutes()
    }

    // ルートを取得する
    private fun loadAllRoutes() {
        viewModelScope.launch(Dispatchers.IO) {
            _routeList.value = routeDao.loadAllRoute()
        }
    }

    // データベースからすべてのルートを取得する
    fun getAllRoutes() {
        // IOスレッドでデータを取得
        viewModelScope.launch(Dispatchers.IO) {
            val routes = routeDao.loadAllRoute()
            // 必要に応じてUIスレッドにデータを反映させる処理を追加
        }
    }
}
