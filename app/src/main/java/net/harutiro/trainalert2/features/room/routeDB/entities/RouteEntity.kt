package net.harutiro.trainalert2.features.room.routeDB.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "route_alert_data")
data class RouteEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "start_longitude") val startLongitude: Double?, // 経度
    @ColumnInfo(name = "start_latitude") val startLatitude: Double?, // 緯度
    @ColumnInfo(name = "end_longitude") val endLongitude: Double?,
    @ColumnInfo(name = "end_latitude") val endLatitude: Double?,
    @ColumnInfo(name = "alert_methods") val alertMethods: Int // アラート方法
)