package net.harutiro.trainalert2.features.room.routeDB.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.harutiro.trainalert2.features.room.routeDB.apis.RouteDao // こちらを修正
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

class RouteRepository(private val routeDao: RouteDao) {


    suspend fun saveRoute(route: RouteEntity) {
        withContext(Dispatchers.IO) {
            routeDao.saveRoute(route)
        }
    }
}
