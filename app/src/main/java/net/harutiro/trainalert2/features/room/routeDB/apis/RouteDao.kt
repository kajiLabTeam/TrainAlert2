package net.harutiro.trainalert2.features.room.routeDB.apis

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

@Dao
interface RouteDao {
    // すべてのルートデータを取得するメソッド
    @Query("SELECT * FROM route_alert_data")
    fun loadAllRoute(): List<RouteEntity>

    // ルートデータを新規作成するメソッド
    @Insert(onConflict = OnConflictStrategy.ABORT) // 新規作成時のみ使用。既存データがあれば失敗。
    fun saveRoute(routeEntity: RouteEntity)

    // ルートデータを更新するメソッド
    @Update
    fun update(routeEntity: RouteEntity)

    // ID を使ってルートデータを削除するメソッド
    @Query("DELETE FROM route_alert_data WHERE id = :routeId")
    suspend fun deleteRoute(routeId: Int)

    //is_enabledがtrueのルートデータを取得するメソッド
    @Query("SELECT * FROM route_alert_data WHERE is_enabled = 1")
    fun getEnabledRoutes(): List<RouteEntity>

    @Query("SELECT * FROM route_alert_data WHERE id = :routeId")
    fun getRouteById(routeId: Int):RouteEntity?

}
