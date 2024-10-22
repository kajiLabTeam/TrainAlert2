package net.harutiro.trainalert2.features.room

import androidx.room.Database
import androidx.room.RoomDatabase
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity
import net.harutiro.trainalert2.features.room.routeDB.apis.RouteDao

@Database(entities = [RouteEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routeDao(): RouteDao
}