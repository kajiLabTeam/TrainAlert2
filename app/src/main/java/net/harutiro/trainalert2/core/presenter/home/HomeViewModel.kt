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
import net.harutiro.trainalert2.features.room.routeDB.repositories.RouteRepository

class HomeViewModel : ViewModel() {


    private val routeRepository: RouteRepository = RouteRepository() // Repositoryのインスタンスを取得

    private val TAG = "HomeViewModel"

    // StateFlowでルートリストを管理
    private val _routeList = MutableStateFlow<List<RouteEntity>>(emptyList())

    val routeList: StateFlow<List<RouteEntity>> = _routeList

    init {
        // データをロードする
        loadAllRoutes()
    }

    // ルートを削除するメソッド
    fun deleteRoute(route: RouteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            routeRepository.deleteRoute(route) // Repositoryの削除メソッドを呼び出す
            loadAllRoutes() // 削除後にルートリストを再ロード
        }
    }

    // ルートを取得する
    fun loadAllRoutes() {
        viewModelScope.launch(Dispatchers.IO) {
            _routeList.value = routeRepository.loadAllRoutes()
        }
    }


}
