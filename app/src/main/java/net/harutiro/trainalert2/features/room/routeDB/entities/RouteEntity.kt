package net.harutiro.trainalert2.features.room.routeDB.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "route_alert_data")
data class RouteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "start_longitude") val startLongitude: Double, // 経度
    @ColumnInfo(name = "start_latitude") val startLatitude: Double, // 緯度
    @ColumnInfo(name = "end_longitude") val endLongitude: Double,
    @ColumnInfo(name = "end_latitude") val endLatitude: Double,
    @ColumnInfo(name = "alert_methods") val alertMethods: Int, // アラート方法
    @ColumnInfo(name = "is_enabled") val isEnabled: Boolean // ルートの有効/無効
){
    companion object {
        const val BOTH = 0
        const val NOTIFICATION = 1
        const val VIBRATION = 2
    }
}