package net.harutiro.trainalert2.features.room.favoriteDB.utils.typeConverter

import androidx.room.TypeConverter
import net.harutiro.trainalert2.features.room.favoriteDB.entities.CityId

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