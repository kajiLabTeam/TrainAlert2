package net.harutiro.trainalert2.features.room.favoriteDB.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = WeatherFavoriteEntity.TABLE_NAME)
data class WeatherFavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var cityId: CityId,
    var createAt: Date,
    var updateAt: Date
){
    companion object{
        const val TABLE_NAME = "weather_favorite_entity"
    }
}