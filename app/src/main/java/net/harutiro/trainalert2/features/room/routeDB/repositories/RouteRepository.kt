package net.harutiro.trainalert2.features.room.routeDB.repositories

import net.harutiro.trainalert2.TrainAlertApplication
import net.harutiro.trainalert2.features.room.routeDB.apis.RouteDao
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

class RouteRepository() {

    private val routeDao: RouteDao = TrainAlertApplication.database.routeDao()

    suspend fun saveRoute(route: RouteEntity) {
        routeDao.saveRoute(route)
    }

    suspend fun deleteRoute(route: RouteEntity) {
        routeDao.deleteRoute(route.id)
    }

    // ルートを更新するメソッド
    suspend fun updateRoute(route: RouteEntity) {
        routeDao.update(route) // DAOのupdateメソッドを呼び出す
    }


    suspend fun loadAllRoutes(): List<RouteEntity> {
        return routeDao.loadAllRoute() // すべてのルートを取得
    }

    //is_enabledがtrueのルートデータを取得するメソッド
    suspend fun getEnabledRoutes(): List<RouteEntity> {
        return routeDao.getEnabledRoutes()
    }

    //idが一致するルートデータを取得するメソッド
    suspend fun getRouteById(id: Int): RouteEntity? {
        return routeDao.getRouteById(id)
    }
}