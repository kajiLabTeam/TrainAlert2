package net.harutiro.trainalert2.features.room.favoriteDB.apis

import net.harutiro.trainalert2.features.room.favoriteDB.entities.CityId
import net.harutiro.trainalert2.features.room.favoriteDB.entities.WeatherFavoriteEntity

interface WeatherFavoriteApi {
    fun insert(weatherFavoriteEntity: WeatherFavoriteEntity)
    fun update(weatherFavoriteEntity: WeatherFavoriteEntity)
    fun delete(weatherFavoriteEntity: WeatherFavoriteEntity)
    fun getAll(): List<WeatherFavoriteEntity>
    fun getById(cityId: CityId): WeatherFavoriteEntity?
}