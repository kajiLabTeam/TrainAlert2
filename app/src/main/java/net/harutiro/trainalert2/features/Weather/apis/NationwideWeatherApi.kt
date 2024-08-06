package net.harutiro.trainalert2.features.Weather.apis

import net.harutiro.trainalert2.features.Weather.entities.CityId
import net.harutiro.trainalert2.features.Weather.entities.Weather

interface NationwideWeatherApi {
    suspend fun getNationwideWeather(cityId: CityId): Weather
}