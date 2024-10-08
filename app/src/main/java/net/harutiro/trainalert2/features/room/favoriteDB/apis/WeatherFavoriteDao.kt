package net.harutiro.trainalert2.features.room.favoriteDB.apis

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import net.harutiro.trainalert2.features.room.favoriteDB.entities.CityId
import net.harutiro.trainalert2.features.room.favoriteDB.entities.WeatherFavoriteEntity

@Dao
interface WeatherFavoriteDao {
    @Insert
    fun insert(weatherFavoriteEntity: WeatherFavoriteEntity)

    @Update
    fun update(weatherFavoriteEntity: WeatherFavoriteEntity)

    @Delete
    fun delete(weatherFavoriteEntity: WeatherFavoriteEntity)

    @Query("SELECT * FROM ${WeatherFavoriteEntity.TABLE_NAME}")
    fun getAll(): List<WeatherFavoriteEntity>

    @Query("SELECT * FROM ${WeatherFavoriteEntity.TABLE_NAME} WHERE CityId = :cityId LIMIT 1")
    fun getByCityId(cityId: CityId): WeatherFavoriteEntity?

}
