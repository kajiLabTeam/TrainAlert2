package net.harutiro.trainalert2.features.room.routeDB.apis

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

@Dao
interface RouteDao {

    // すべてのルートを取得
    @Query("SELECT * FROM route_alert_data")
    suspend fun loadAllRoute(): List<RouteEntity>

    // ルートを保存
    @Insert(onConflict = OnConflictStrategy.ABORT) // 新規作成時に既存データがあれば失敗
    suspend fun saveRoute(routeEntity: RouteEntity)

    // ルートデータを新規作成するメソッド
    @Insert(onConflict = OnConflictStrategy.ABORT) // 新規作成時のみ使用。既存データがあれば失敗。
    fun saveRoute(routeEntity: RouteEntity)

    // ルートを更新
    @Update
    suspend fun update(routeEntity: RouteEntity)

    // ルートを削除
    @Delete
    suspend fun deleteRoute(routeEntity: RouteEntity)
}

    // ID を使ってルートデータを削除するメソッド
    @Query("DELETE FROM route_alert_data WHERE id = :routeId")
    suspend fun deleteRoute(routeId: Int)
}

