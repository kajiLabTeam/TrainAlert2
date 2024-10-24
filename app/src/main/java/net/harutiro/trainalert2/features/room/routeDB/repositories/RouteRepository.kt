package net.harutiro.trainalert2.features.room.routeDB.repositories

import net.harutiro.trainalert2.TrainAlertApplication
import net.harutiro.trainalert2.features.room.routeDB.apis.RouteDao
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

class RouteRepository {

    private val routeDao: RouteDao = TrainAlertApplication.database.routeDao()

    // ルートを保存するメソッド
    suspend fun saveRoute(route: RouteEntity) {
        routeDao.saveRoute(route)
    }

    // ルート全てを取得するメソッドを追加
    suspend fun getAllRoutes(): List<RouteEntity> {
        return routeDao.getAllRoutes()
    }
}
