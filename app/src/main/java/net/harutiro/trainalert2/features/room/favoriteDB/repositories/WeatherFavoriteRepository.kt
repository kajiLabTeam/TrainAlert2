package net.harutiro.trainalert2.features.room.favoriteDB.repositories

import kotlinx.coroutines.Deferred
import net.harutiro.trainalert2.features.room.favoriteDB.entities.CityId
import net.harutiro.trainalert2.features.room.favoriteDB.entities.WeatherFavoriteEntity

interface WeatherFavoriteRepository {
    fun insertFavorite(cityId: CityId) : Deferred<Result<Unit>>
    fun deleteFavorite(cityId:CityId): Deferred<Result<Unit>>
    fun getFavoriteList(): Deferred<List<WeatherFavoriteEntity>>
    fun isFavorite(cityId: CityId): Deferred<Boolean>
}