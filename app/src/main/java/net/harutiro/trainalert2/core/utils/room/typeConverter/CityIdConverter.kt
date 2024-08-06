package net.harutiro.trainalert2.core.utils.room.typeConverter

import androidx.room.TypeConverter
import net.harutiro.trainalert2.features.Weather.entities.CityId

class CityIdConverter {
    @TypeConverter
    fun fromCityId(cityId: CityId): String {
        return cityId.id
    }

    @TypeConverter
    fun toCityId(value: String): CityId? {
        return CityId.idToCityId(value)
    }
}