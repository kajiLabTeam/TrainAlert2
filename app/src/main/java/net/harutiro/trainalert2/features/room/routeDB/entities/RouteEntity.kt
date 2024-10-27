package net.harutiro.trainalert2.features.room.routeDB.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "route_alert_data")
data class RouteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "start_longitude") val startLongitude: Double?, // 始点の経度
    @ColumnInfo(name = "start_latitude") val startLatitude: Double?, // 始点の緯度
    @ColumnInfo(name = "end_longitude") val endLongitude: Double?, // 目的地の経度
    @ColumnInfo(name = "end_latitude") val endLatitude: Double?, // 目的地の緯度
    @ColumnInfo(name = "alert_methods") val alertMethods: Int, // アラート方法を数値型で保存
    @ColumnInfo(name = "is_enabled") val isEnabled: Boolean // ルートの有効/無効
) {
    companion object {
        const val NOTIFICATION = 1
        const val VIBRATION = 2
        const val BOTH = 3
    }
}
