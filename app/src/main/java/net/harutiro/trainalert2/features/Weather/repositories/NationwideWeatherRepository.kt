package net.harutiro.trainalert2.features.Weather.repositories

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.Job
import net.harutiro.trainalert2.features.Weather.entities.CityId
import net.harutiro.trainalert2.features.Weather.entities.Weather

interface NationwideWeatherRepository {
    suspend fun getNationwideWeather(weathers: SnapshotStateList<Weather>) : Job
    suspend fun getPrefectureWeather(city : CityId) : Weather
}