package net.harutiro.trainalert2.features.room.routeDB.repositories

import net.harutiro.trainalert2.TrainAlertApplication
import net.harutiro.trainalert2.features.room.routeDB.apis.RouteDao
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

class RouteRepository() {

    private val routeDao: RouteDao = TrainAlertApplication.database.routeDao()

    suspend fun saveRoute(route: RouteEntity) {
        routeDao.saveRoute(route)
    }
}