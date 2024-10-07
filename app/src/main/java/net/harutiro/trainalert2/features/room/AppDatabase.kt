package net.harutiro.trainalert2.features.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.harutiro.trainalert2.features.room.favoriteDB.utils.typeConverter.CityIdConverter
import net.harutiro.trainalert2.features.room.favoriteDB.utils.typeConverter.DateConverter
import net.harutiro.trainalert2.features.room.favoriteDB.apis.WeatherFavoriteDao
import net.harutiro.trainalert2.features.room.favoriteDB.entities.WeatherFavoriteEntity

@Database(entities = [WeatherFavoriteEntity::class], version = 1)
@TypeConverters(CityIdConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherFavoriteDao(): WeatherFavoriteDao
}