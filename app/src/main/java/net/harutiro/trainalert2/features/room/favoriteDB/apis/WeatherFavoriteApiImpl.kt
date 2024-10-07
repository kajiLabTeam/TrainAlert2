package net.harutiro.trainalert2.features.room.favoriteDB.apis

import net.harutiro.trainalert2.Application
import net.harutiro.trainalert2.features.Weather.entities.CityId
import net.harutiro.trainalert2.features.room.favoriteDB.entities.WeatherFavoriteEntity

class WeatherFavoriteApiImpl : WeatherFavoriteApi {
    private val dao = Application.database.weatherFavoriteDao()

    override fun insert(weatherFavoriteEntity: WeatherFavoriteEntity) {
        dao.insert(weatherFavoriteEntity)
    }

    override fun update(weatherFavoriteEntity: WeatherFavoriteEntity) {
        dao.update(weatherFavoriteEntity)
    }

    override fun delete(weatherFavoriteEntity: WeatherFavoriteEntity) {
        dao.delete(weatherFavoriteEntity)
    }

    override fun getAll(): List<WeatherFavoriteEntity> {
        return dao.getAll()
    }

    override fun getById(cityId: CityId): WeatherFavoriteEntity? {
        return dao.getByCityId(cityId)
    }
}